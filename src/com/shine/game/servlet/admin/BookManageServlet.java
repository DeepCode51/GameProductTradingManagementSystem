package com.shine.game.servlet.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.shine.game.bean.Book;
import com.shine.game.bean.Catalog;
import com.shine.game.bean.PageBean;
import com.shine.game.bean.UpLoadImg;
import com.shine.game.dao.BookDao;
import com.shine.game.dao.CatalogDao;
import com.shine.game.dao.UpLoadImgDao;
import com.shine.game.dao.impl.BookDaoImpl;
import com.shine.game.dao.impl.CatalogDaoImpl;
import com.shine.game.dao.impl.UpLoadImgDaoImpl;
import com.shine.game.util.RanUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class BookManageServlet
 */
@WebServlet("/jsp/admin/BookManageServlet")
public class BookManageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String BOOKLIST_PATH = "bookManage/bookList.jsp";// 饰品列表页面地址
    private static final String BOOKADD_PATH = "bookManage/bookAdd.jsp";// 饰品增加页面地址
    private static final String BOOKEDIT_PATH = "bookManage/bookEdit.jsp";// 饰品修改页面地址
    private static final String BOOKDETAIL_PATH = "bookManage/bookDetail.jsp";// 饰品详情页面地址
    private static final String BOOKIMGDIR_PATH = "images/book/bookimg/";//饰品图片保存文件夹相对路径

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "list":
                bookList(request, response);
                break;
            case "detail":
                bookDetail(request, response);
                break;
            case "addReq":
                bookAddReq(request, response);
                break;
            case "add":
                bookAdd(request, response);
                break;
            case "edit":
                bookEdit(request, response);
                break;
            case "update":
                bookUpdate(request, response);
                break;
            case "find":
                bookFind(request, response);
                break;
            case "updateImg":
                updateImg(request, response);
                break;
            case "del":
                bookDel(request, response);
                break;
            case "batDel":
                bookBatDel(request, response);
                break;
        }
    }

    //饰品批量删除
    private void bookBatDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ids = request.getParameter("ids");
        BookDao bd = new BookDaoImpl();
        UpLoadImgDao uid = new UpLoadImgDaoImpl();
        File contextPath = new File(request.getServletContext().getRealPath("/"));

        String imgIds = bd.findimgIdByIds(ids);//批量查询图片的id并组成一组字符串

        List<UpLoadImg> list = uid.findImgByIds(imgIds);
        if (bd.bookBatDelById(ids)) {
            request.setAttribute("bookMessage", "饰品已批量删除");
            if (uid.imgBatDelById(imgIds)) {
                for (UpLoadImg uli : list) {
                    //批量删除本地文件
                    File f = new File(contextPath, uli.getImgSrc());
                    if (f.exists()) {
                        f.delete();
                    }
                }
            }
        } else {
            request.setAttribute("bookMessage", "饰品批量删除失败");
        }
        //用户删除成功失败都跳转到用户列表页面
        bookList(request, response);//通过servlet中listUser跳到用户列表
    }

    //饰品删除
    private void bookDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        File contextPath = new File(request.getServletContext().getRealPath("/"));
        BookDao bd = new BookDaoImpl();
        UpLoadImgDao uid = new UpLoadImgDaoImpl();
        Book book = bd.findBookById(id);
        //这里先删除数据库饰品信息，再删除饰品图片及本地硬盘图片信息
        if (bd.bookDelById(id)) {
            request.setAttribute("bookMessage", "饰品已删除");
            if (uid.imgDelById(book.getImgId())) {
                //删除本地文件
                File f = new File(contextPath, book.getUpLoadImg().getImgSrc());
                if (f.exists()) {
                    f.delete();
                }
            }
        } else {
            request.setAttribute("bookMessage", "饰品删除失败");
        }

        //用户删除成功失败都跳转到用户列表页面
        bookList(request, response);//通过servlet中listUser跳到用户列表

    }

    //饰品更新
    private void bookUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDao bookDao = new BookDaoImpl();
        Book book = new Book();
        book.setBookId(Integer.parseInt(request.getParameter("bookId")));
        book.setCatalogId(Integer.parseInt(request.getParameter("catalog")));
        book.setAuthor(request.getParameter("author"));
        book.setPress(request.getParameter("press"));
        book.setBookName(request.getParameter("bookName"));
        book.setPrice(Double.parseDouble(request.getParameter("price")));
        book.setDescription(request.getParameter("description"));

        if (bookDao.bookUpdate(book)) {
            request.setAttribute("bookMessage", "修改成功");
            bookList(request, response);
        } else {
            request.setAttribute("bookMessage", "图片失败");
            request.setAttribute("bookInfo", bookDao.findBookById(book.getBookId()));
            request.getRequestDispatcher(BOOKEDIT_PATH).forward(request, response);
        }
    }

    // 更新饰品图片
    private void updateImg(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        boolean flag = false;
        String imgSrc = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        String imgName = null;
        String contentType = null;

        BookDao bookDao = new BookDaoImpl();
        UpLoadImgDao upImgDao = new UpLoadImgDaoImpl();

        File contextPath = new File(request.getServletContext().getRealPath("/"));
        File dirPath = new File(contextPath, BOOKIMGDIR_PATH);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        System.out.println(dirPath.getPath());

        DiskFileItemFactory dfif = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(dfif);
        List<FileItem> parseRequest = null;
        try {
            parseRequest = servletFileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator<FileItem> iterator = parseRequest.iterator();
        while (iterator.hasNext()) {
            FileItem fileItem = iterator.next();
            if (!fileItem.isFormField()) {

                inputStream = fileItem.getInputStream();
                contentType = fileItem.getContentType();
                if ("image/jpeg".equals(contentType)) {
                    imgName = RanUtil.getUUID() + ".jpg";
                    flag = true;
                }
                if ("image/png".equals(contentType)) {
                    imgName = RanUtil.getUUID() + ".png";
                    flag = true;
                }

            }

        }
        if (flag) {
            imgSrc = BOOKIMGDIR_PATH + imgName;
            outputStream = new FileOutputStream(new File(contextPath, imgSrc));

            IOUtils.copy(inputStream, outputStream);
            outputStream.close();
            inputStream.close();
            //根据饰品id去查询图片信息
            Book book = bookDao.findBookById(bookId);
            UpLoadImg upImg = book.getUpLoadImg();
            // 删除旧图片文件如果存在
            File oldImg = new File(contextPath, book.getUpLoadImg().getImgSrc());
            if (oldImg.exists()) {
                oldImg.delete();
            }
            System.out.println("old:" + oldImg.getPath());
            upImg.setImgName(imgName);
            upImg.setImgSrc(imgSrc);
            upImg.setImgType(contentType);


            if (upImgDao.imgUpdate(upImg)) {
                request.setAttribute("bookMessage", "图片修改成功");
            } else {
                request.setAttribute("bookMessage", "图片修改失败");
            }
        } else {
            request.setAttribute("bookMessage", "图片修改失败");
        }
        bookEdit(request, response);
    }

    // 获取饰品分类信息
    private void bookAddReq(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CatalogDao cd = new CatalogDaoImpl();
        request.setAttribute("catalog", cd.getCatalog());
        request.getRequestDispatcher(BOOKADD_PATH).forward(request, response);

    }

    // 饰品增加
    private void bookAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean flag = false;

        Map<String, String> map = new HashMap<>();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File dirPath = new File(request.getServletContext().getRealPath("/") + BOOKIMGDIR_PATH);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        DiskFileItemFactory dfif = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(dfif);
        // 解决乱码
        servletFileUpload.setHeaderEncoding("ISO8859_1");

        List<FileItem> parseRequest = null;
        try {
            parseRequest = servletFileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        Iterator<FileItem> iterator = parseRequest.iterator();

        while (iterator.hasNext()) {
            FileItem fileItem = iterator.next();
            // 判断是否是表单的普通字段true为普通表单字段，false为上传文件内容
            if (fileItem.isFormField()) {
                String name = new String(fileItem.getFieldName().getBytes("ISO8859_1"), "utf-8");
                String value = new String(fileItem.getString().getBytes("ISO8859_1"), "utf-8");
                map.put(name, value);
            } else {
                String imgName = null;

                String contentType = fileItem.getContentType();

                if ("image/jpeg".equals(contentType)) {
                    imgName = RanUtil.getUUID() + ".jpg";
                    flag = true;
                }
                if ("image/png".equals(contentType)) {
                    imgName = RanUtil.getUUID() + ".png";
                    flag = true;
                }
                if (flag) {
                    inputStream = fileItem.getInputStream();
                    File file = new File(dirPath, imgName);
                    outputStream = new FileOutputStream(file);
                    // 保存img信息到map集合中，后面传入对象使用
                    map.put("imgName", imgName);
                    map.put("imgSrc", BOOKIMGDIR_PATH + imgName);
                    map.put("imgType", contentType);
                }

            }
        }
        // 如果上传的内容小于3个必填项或者图片没有或类型不正确返回
        if (map.size() < 3 || !flag) {
            request.setAttribute("bookMessage", "饰品添加失败");
            bookAddReq(request, response);
        } else {
            // 验证通过才可以保存图片流到本地
            IOUtils.copy(inputStream, outputStream);
            outputStream.close();
            inputStream.close();

            // 把map集合中存储的表单数据提取出来转换为book对象
            // 这里要求饰品增加的字段要和数据库字段一致，不然map集合转对象会出错
            Book book = new Book();
            book.setBookName(map.get("bookName"));
            book.setPrice(Double.parseDouble(map.get("price")));
            book.setDescription(map.get("desc"));
            book.setAuthor(map.get("author"));
            book.setPress(map.get("press"));
            // 饰品分类信息
            Catalog catalog = book.getCatalog();
            catalog.setCatalogId(Integer.parseInt(map.get("catalog")));
            // 图片信息
            UpLoadImg upLoadImg = book.getUpLoadImg();
            upLoadImg.setImgName(map.get("imgName"));
            upLoadImg.setImgSrc(map.get("imgSrc"));
            upLoadImg.setImgType(map.get("imgType"));

            // 增加饰品先增加饰品图片,饰品图片增加成功了在添加饰品信息
            UpLoadImgDao uid = new UpLoadImgDaoImpl();
            if (uid.imgAdd(book.getUpLoadImg())) {
                // 获取饰品图片添加后的id
                Integer imgId = uid.findIdByImgName(upLoadImg.getImgName());
                upLoadImg.setImgId(imgId);
                BookDao bd = new BookDaoImpl();
                if (bd.bookAdd(book)) {
                    request.setAttribute("bookMessage", "饰品添加成功");
                    bookList(request, response);
                } else {
                    request.setAttribute("bookMessage", "饰品添加失败");
                    bookAddReq(request, response);
                }
            } else {
                // 图片添加失败就判定饰品添加失败
                request.setAttribute("bookMessage", "饰品添加失败");
                bookAddReq(request, response);
            }

        }
    }

    // 获取饰品列表
    private void bookList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        int maxSize = Integer.parseInt(request.getServletContext().getInitParameter("maxPageSize"));
        BookDao bd = new BookDaoImpl();
        PageBean pb = new PageBean(curPage, maxSize, bd.bookReadCount());
        System.out.println(bd.bookList(pb).size());
        request.setAttribute("pageBean", pb);
        request.setAttribute("bookList", bd.bookList(pb));
        request.getRequestDispatcher(BOOKLIST_PATH).forward(request, response);
    }


    // 饰品详情页
    private void bookDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        BookDao bd = new BookDaoImpl();
        request.setAttribute("bookInfo", bd.findBookById(Integer.parseInt(id)));
        request.getRequestDispatcher(BOOKDETAIL_PATH).forward(request, response);

    }

    // 接收饰品修改请求
    private void bookEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        BookDao bookDao = new BookDaoImpl();
        CatalogDao catalogDao = new CatalogDaoImpl();
        request.setAttribute("catalog", catalogDao.getCatalog());//获取饰品分类信息
        request.setAttribute("bookInfo", bookDao.findBookById(bookId));//获取饰品信息byId
        request.getRequestDispatcher(BOOKEDIT_PATH).forward(request, response);
    }

    // ajax查找饰品是否存在
    private void bookFind(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bookName = request.getParameter("param");
        BookDao bd = new BookDaoImpl();
        // 这里实例化json对象需要导入6个jar包（
        // commons-lang-2.4.jar
        // ,commons-collections-3.2.1.jar,commons-beanutils-1.8.3.jar
        // json-lib-2.4-jdk15.jar ,ezmorph-1.0.6.jar ,commons-logging-1.1.3.jar）
        JSONObject json = new JSONObject();
        if (bd.findBookByBookName(bookName)) {
            json.put("info", "该饰品已存在");
            json.put("status", "n");
        } else {
            json.put("info", "输入正确");
            json.put("status", "y");
        }
        response.getWriter().write(json.toString());

    }

}

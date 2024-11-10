package com.shine.game.servlet.book;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shine.game.bean.*;
import com.shine.game.dao.BookDao;
import com.shine.game.dao.CatalogDao;
import com.shine.game.dao.UpLoadImgDao;
import com.shine.game.dao.impl.BookDaoImpl;
import com.shine.game.dao.impl.CatalogDaoImpl;
import com.shine.game.dao.impl.UpLoadImgDaoImpl;
import com.shine.game.util.RanUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

/**
 * Servlet implementation class BookList
 */
@WebServlet("/BookList")
public class BookList extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int MAX_LIST_SIZE = 12;
    private static final String BOOKLIST_PATH = "jsp/book/booklist.jsp";

    private static final String BOOKDETAIL_PATH = "bookManage/bookDetail.jsp";// 饰品详情页面地址
    private static final String BOOKIMGDIR_PATH = "images/book/bookimg/";//饰品图片保存文件夹相对路径

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("方法：" + action);
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "d":
                break;
            case "mySellList":
                mySellList(request, response);
                break;
            case "del":
                bookDel(request, response);
                break;
            case "batDel":
                bookBatDel(request, response);
                break;
            case "addReq":
                bookAddReq(request, response);
                break;
            case "list"://默认全部饰品列表
                bookList(request, response);
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
        }
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
            mySellList(request, response);
        } else {
            request.setAttribute("bookMessage", "图片失败");
            request.setAttribute("bookInfo", bookDao.findBookById(book.getBookId()));
            request.getRequestDispatcher("jsp/book/sellBookEdit.jsp").forward(request, response);
        }
    }

    // 接收饰品修改请求
    private void bookEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        BookDao bookDao = new BookDaoImpl();
        CatalogDao catalogDao = new CatalogDaoImpl();
        request.setAttribute("catalog", catalogDao.getCatalog());//获取饰品分类信息
        request.setAttribute("bookInfo", bookDao.findBookById(bookId));//获取饰品信息byId
        request.getRequestDispatcher("jsp/book/sellBookEdit.jsp").forward(request, response);
    }

    private void bookList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDao bd = new BookDaoImpl();
        int curPage = 1;
        String page = request.getParameter("page");
        String name = request.getParameter("name");
        System.out.println("搜索名称：" + name);
        if (page != null) {
            curPage = Integer.parseInt(page);
        }

        PageBean pb = null;
        List<Book> bookList = new ArrayList<Book>();
        String catalogIdStr = request.getParameter("catalogId");//获取有没有分类id，没有就是查全部

        if (catalogIdStr != null) {
            int catalogId = Integer.parseInt(catalogIdStr);
            pb = new PageBean(curPage, MAX_LIST_SIZE, bd.bookReadCount(catalogId));
            bookList = bd.bookList(pb, catalogId);
            if (bookList.size() > 0) {
                request.setAttribute("title", bookList.get(0).getCatalog().getCatalogName());//从返回的分类集合中第一个获取数据的分类
            }
        } else {
            if (name != null) {
                pb = new PageBean(curPage, MAX_LIST_SIZE, bd.bookReadCountLikeName(name));
                bookList = bd.bookListLikeName(pb, name);
                request.setAttribute("title", "关于[" + name + "]搜索结果");
            } else {
                pb = new PageBean(curPage, MAX_LIST_SIZE, bd.bookReadCount());
                bookList = bd.bookList(pb);
                request.setAttribute("title", "所有饰品");
            }
        }
        request.setAttribute("pageBean", pb);
        request.setAttribute("queryName", name);
        request.setAttribute("bookList", bookList);
        request.getRequestDispatcher(BOOKLIST_PATH).forward(request, response);
    }


    // 获取饰品列表
    private void mySellList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //当前登录者
        User user = (User) request.getSession().getAttribute("landing");
        if (user == null) {
            response.sendRedirect("jsp/book/reg.jsp?type=login");
        } else {
            System.out.println("我的出售");
            int curPage = 1;
            String page = request.getParameter("page");
            if (page != null) {
                curPage = Integer.parseInt(page);
            }
            int maxSize = Integer.parseInt(request.getServletContext().getInitParameter("maxPageSize"));
            BookDao bd = new BookDaoImpl();
            PageBean pb = new PageBean(curPage, maxSize, bd.bookReadCountByUserId(user.getUserId()));
            System.out.println(bd.bookList(pb).size());
            request.setAttribute("pageBean", pb);
            request.setAttribute("bookList", bd.bookListByUserId(pb, user.getUserId()));
            request.getRequestDispatcher("jsp/book/myselllist.jsp").forward(request, response);
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
        mySellList(request, response);//通过servlet中listUser跳到用户列表
    }

    //饰品删除
    private void bookDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("用户删掉饰品信息");
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

        System.out.println("用户删掉出售的饰品");

        //用户删除成功失败都跳转到用户列表页面
        mySellList(request, response);//通过servlet中listUser跳到用户列表
    }


    // 获取饰品分类信息
    private void bookAddReq(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //当前登录者
        User user = (User) request.getSession().getAttribute("landing");
        if (user == null) {
            response.sendRedirect("jsp/book/reg.jsp?type=login");
        } else {
            CatalogDao cd = new CatalogDaoImpl();
            request.setAttribute("catalog", cd.getCatalog());
            request.getRequestDispatcher("jsp/book/sellBookAdd.jsp").forward(request, response);
        }
    }


    // 饰品增加
    private void bookAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //当前登录者
        User user = (User) request.getSession().getAttribute("landing");

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
            book.setSellUserId(user.getUserId());
            book.setSellUserName(user.getName());
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
                    mySellList(request, response);
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


}

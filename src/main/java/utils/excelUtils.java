/*
package utils;

import entity.Category;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class excelUtils {
    public void exportExcel(){
        //用于导出的数据集合
        List<Category> dataset = new ArrayList<Category>();
        //填充dataset
        for (int j = 0; j < 10; j++) {
            Category category = new Category();
            dataset.add(category);
        }

        //临时文件
        File tempFile = null;
        try {
            //Excel导出工具类
            ExportExcel<PBillBean> ex = new ExportExcel<PBillBean>();
            //导出的标题列
            String[] headers = { "标题1", "标题2", "标题3", "标题4",    "标题5", "标题6" };
            //时间格式化
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            //要保存的文件名
            String filename = "bill_" + format.format(new Date()) + ".xls";
            //要保存的根目录
            String rootDir = request.getSession().getServletContext().getRealPath("/");
            //要保存的目录路径
            String path = rootDir + File.separator + "tempfile";
            File saveDir = new File(path);
            if (!saveDir.exists()) {
                saveDir.mkdirs();// 如果文件不存在则创建文件夹
            }
            //文件路径
            path = path + File.separator + filename;
            tempFile = new File(path);   //初始化临时文件
            //输出流
            OutputStream out = new FileOutputStream(tempFile);
            //实例化Excel表格
            HSSFWorkbook workbook = new HSSFWorkbook();
            //创建工作表单
            String[] sheetNames = { "对账报表" };
            for (int i = 0; i < sheetNames.length; i++) {
                workbook.createSheet(sheetNames[i]);
            }
            //导出到Excel
            ex.exportExcel(sheetNames[0], headers, dataset, out,
                    "yyyy-MM-dd HH:mm", workbook);
            try {
                //保存文件
                workbook.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
            // 以流的形式下载文件。
            BufferedInputStream fis = new BufferedInputStream(
                    new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + tempFile.length());
            OutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();// 删除临时文件
            }
        }
    }


}
*/

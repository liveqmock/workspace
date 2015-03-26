package com.yazuo.erp.system.controller;

import com.yazuo.erp.system.exception.SystemBizException;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 上传附件
 */
@Controller
@RequestMapping("attachment")
public class SysAttachmentController extends AbstractBasicController {

    @Resource
    private SysAttachmentService sysAttachmentService;

    @RequestMapping("download")
    @ResponseBody
    public void downloadAttachment(Integer attachmentId,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        SysAttachmentVO attachmentVO = this.sysAttachmentService.getSysAttachmentById(attachmentId);
        String root = this.getApplicationRoot(request.getSession());
        String filename = root + "/"  + attachmentVO.getAttachmentPath() + "/" + attachmentVO.getAttachmentName();
        String outFilename = attachmentVO.getAttachmentName();
        try {
            outFilename = URLEncoder.encode(attachmentVO.getOriginalFileName(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File file = new File(filename);
        OutputStream out = null;
        try {
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + outFilename);
            response.setContentType("application/octet-stream;charset=utf-8");
            out = response.getOutputStream();
            IOUtils.copyLarge(FileUtils.openInputStream(file), out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    throw new SystemBizException("关闭IO出错");
                }
            }
        }


    }
}

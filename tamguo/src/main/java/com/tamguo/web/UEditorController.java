package com.tamguo.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.tamguo.config.ueditor.UEditorConfig;
import com.tamguo.config.ueditor.Ueditor;
import com.tamguo.dao.redis.CacheService;
import com.tamguo.util.DateUtils;
import com.tamguo.util.Setting;

@Controller
public class UEditorController {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	@Value("${file.storage.path}")
	private String fileStoragePath;
	@Autowired
	private Setting setting;
	@Autowired
	private CacheService cacheService;
	private static final String UEDITOR_NO_FORMAT = "00000";
	private static final String UEDITOR_PREFIX = "UEDITOR";
	
	
	@RequestMapping(value="/ueditor")
    @ResponseBody
    public JSONObject ueditor(HttpServletRequest request) {
        return JSONObject.parseObject(UEditorConfig.UEDITOR_CONFIG);
    }

    @RequestMapping(value="/imgUpload")
    @ResponseBody
    public Ueditor imgUpload(MultipartFile upfile) throws IOException {
    	if (!upfile.isEmpty()) {
			InputStream in = null;
			OutputStream out = null;
			
			try {
				String path = fileStoragePath + DateUtils.format(new Date(), "yyyyMMdd");
				File dir = new File(path);
				if (!dir.exists())
					dir.mkdirs();
				String fileName = this.getUEditorNo() + upfile.getOriginalFilename().substring(upfile.getOriginalFilename().lastIndexOf("."));
				File serverFile = new File(dir + File.separator + fileName);
				in = upfile.getInputStream();
				out = new FileOutputStream(serverFile);
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = in.read(b)) > 0) {
					out.write(b, 0, len);
				}
				out.close();
				in.close();
				logger.info("Server File Location=" + serverFile.getAbsolutePath());

		        Ueditor ueditor = new Ueditor();
		        ueditor.setState("SUCCESS");
		        ueditor.setUrl(setting.domain + "files" + "/" +DateUtils.format(new Date(), "yyyyMMdd") + "/" + fileName);
		        return ueditor;
			} catch (Exception e) {
				Ueditor ueditor = new Ueditor();
		        ueditor.setState("ERROR");
		        ueditor.setTitle("上传失败");
		        return ueditor;
			} finally {
				if (out != null) {
					out.close();
					out = null;
				}

				if (in != null) {
					in.close();
					in = null;
				}
			}
		} else {
			Ueditor ueditor = new Ueditor();
	        ueditor.setState("ERROR");
	        ueditor.setTitle("File is empty");
	        return ueditor;
		}
    }
    
	private String getUEditorNo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String format = sdf.format(new Date());
		DecimalFormat df = new DecimalFormat(UEDITOR_NO_FORMAT);
		String key = UEDITOR_PREFIX + format;
		Long incr = cacheService.incr(key);
		String avatorNo = UEDITOR_PREFIX + df.format(incr);
		return avatorNo;
	}

}

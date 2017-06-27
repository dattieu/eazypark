package prototype.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.springframework.web.multipart.MultipartFile;

public final class FileHandler {
	
	public static final String getAbsolutePath(String fileName){
		return Paths.get(fileName).toAbsolutePath().toString();
	}
	
	public static final File createDirectory(String dirPath){
		File dir = new File(dirPath);
		if (!dir.exists())
			dir.mkdirs();
		return dir;
	}
	
	public static final File createFile(File dirPath, String fileName){
		return new File(dirPath.getAbsolutePath() + File.separator + fileName);
	}
	
	public static final String createUniqueName(String prefix, String suffix, String extension){
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		return prefix + "_" + timeStamp + "_" + suffix + extension;
		
	}
	
	public static final void writeFileAsBytes(MultipartFile file, File outFile) throws IOException{
		byte[] bytes = file.getBytes();
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(outFile));
		stream.write(bytes);
		stream.close();
	}
	
}
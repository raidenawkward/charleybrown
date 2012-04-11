/**
 * @Title: CBZipFactory.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-11
 */
package com.android.cb.source;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author raiden
 *
 * @Description methods for zip file operations
 */
public class CBZipFactory {

	private static final int ZIP_BUFFER_SIZE = 1024 * 1024;

	public static void zipFiles(List<File> resFileList, File zipFile) throws IOException {
		ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(
        zipFile), ZIP_BUFFER_SIZE));
		for (File resFile : resFileList) {
			zipFile(resFile, zipout, "");
		}
		zipout.close();
	}

	public static void zipFiles(List<File> resFileList, File zipFile, String comment)
	            throws IOException {
        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(
                zipFile), ZIP_BUFFER_SIZE));
        for (File resFile : resFileList) {
            zipFile(resFile, zipout, "");
        }
        zipout.setComment(comment);
        zipout.close();
    }

	public static void upZipFile(File zipFile, String folderPath) throws ZipException, IOException {

        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
            ZipEntry entry = ((ZipEntry)entries.nextElement());
            InputStream in = zf.getInputStream(entry);
            String str = folderPath + File.separator + entry.getName();
            str = new String(str.getBytes(CBSettings.getStringValue(CBSettings.CB_SETTINGS_XML_ENCODING)), "GB2312");
            File desFile = new File(str);
            if (!desFile.exists()) {
                File fileParentDir = desFile.getParentFile();
                if (!fileParentDir.exists()) {
                    fileParentDir.mkdirs();
                }
                desFile.createNewFile();
            }
            OutputStream out = new FileOutputStream(desFile);
            byte buffer[] = new byte[ZIP_BUFFER_SIZE];
            int realLength;
            while ((realLength = in.read(buffer)) > 0) {
                out.write(buffer, 0, realLength);
            }
            in.close();
            out.close();
        }
    }

	public static ArrayList<File> upZipSelectedFile(File zipFile, String folderPath,
			            String nameContains) throws ZipException, IOException {
        ArrayList<File> fileList = new ArrayList<File>();

        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdir();
        }

        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
            ZipEntry entry = ((ZipEntry)entries.nextElement());
            if (entry.getName().contains(nameContains)) {
                InputStream in = zf.getInputStream(entry);
                String str = folderPath + File.separator + entry.getName();
                str = new String(str.getBytes(CBSettings.getStringValue(CBSettings.CB_SETTINGS_XML_ENCODING)), "GB2312");

                File desFile = new File(str);
                if (!desFile.exists()) {
                    File fileParentDir = desFile.getParentFile();
                    if (!fileParentDir.exists()) {
                        fileParentDir.mkdirs();
                    }
                    desFile.createNewFile();
                }
                OutputStream out = new FileOutputStream(desFile);
                byte buffer[] = new byte[ZIP_BUFFER_SIZE];
                int realLength;
                while ((realLength = in.read(buffer)) > 0) {
                    out.write(buffer, 0, realLength);
                }
                in.close();
                out.close();
                fileList.add(desFile);
            }
        }
        return fileList;
    }

	public static ArrayList<String> getEntriesNames(File zipFile) throws ZipException, IOException {
        ArrayList<String> entryNames = new ArrayList<String>();
        Enumeration<?> entries = getEntriesEnumeration(zipFile);
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry)entries.nextElement());
            entryNames.add(new String(getEntryName(entry).getBytes(CBSettings.getStringValue(CBSettings.CB_SETTINGS_XML_ENCODING)), "8859_1"));
        }
        return entryNames;
    }

	public static Enumeration<?> getEntriesEnumeration(File zipFile) throws ZipException,
	            IOException {
        ZipFile zf = new ZipFile(zipFile);
        return zf.entries();
    }

	public static String getEntryName(ZipEntry entry) throws UnsupportedEncodingException {
        return new String(entry.getName().getBytes(CBSettings.getStringValue(CBSettings.CB_SETTINGS_XML_ENCODING)), "8859_1");
    }

	public static String getEntryComment(ZipEntry entry) throws UnsupportedEncodingException {
        return new String(entry.getComment().getBytes(CBSettings.getStringValue(CBSettings.CB_SETTINGS_XML_ENCODING)), "8859_1");
    }

	private static void zipFile(File resFile, ZipOutputStream zipout,
			String rootPath) throws FileNotFoundException, IOException {

		rootPath = rootPath + (rootPath.trim().length() == 0 ? "" : File.separator) + resFile.getName();
		rootPath = new String(rootPath.getBytes("8859_1"), CBSettings.getStringValue(CBSettings.CB_SETTINGS_XML_ENCODING));

		if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            for (File file : fileList) {
                zipFile(file, zipout, rootPath);
            }
        } else {
			byte buffer[] = new byte[ZIP_BUFFER_SIZE];
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile), ZIP_BUFFER_SIZE);
			zipout.putNextEntry(new ZipEntry(rootPath));
			int realLength;
			while ((realLength = in.read(buffer)) != -1) {
			    zipout.write(buffer, 0, realLength);
			}
			in.close();
			zipout.flush();
			zipout.closeEntry();
		}
	}

}

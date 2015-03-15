package com.fangger.utils.apk;

/**
 * Created by popo on 2014/10/13.
 */
public class ApkUtils {
    /*
    private static String cmdDecode(String apkFile) throws AndrolibException {
        if(!apkFile.endsWith(".apk")){
            throw new IllegalArgumentException("不是apk类型文件");
        }

        String outName = apkFile.substring(0,apkFile.lastIndexOf(".apk"));

        ApkDecoder decoder = new ApkDecoder();

        decoder.setForceDelete(true);
        File outDir = new File(outName);
        decoder.setOutDir(outDir);
        decoder.setApkFile(new File(apkFile));


        try
        {
            decoder.decode();
        } catch (OutDirExistsException ex) {
            System.out.println("Destination directory (" + outDir.getAbsolutePath() + ") " + "already exists. Use -f switch if you want to overwrite it.");

            System.exit(1);
        } catch (InFileNotFoundException ex) {
            System.out.println("Input file (" + apkFile + ") " + "was not found or was not readable.");

            System.exit(1);
        } catch (CantFindFrameworkResException ex) {
            System.out.println("Can't find framework resources for package of id: " + String.valueOf(ex.getPkgId()) + ". You must install proper " + "framework files, see project website for more info.");

            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Could not modify file. Please ensure you have permission.");

            System.exit(1);
        }

         return outName;


*//*

        for (int i = 0; i < args.length; i++) {
            String opt = args[i];
            if (!opt.startsWith("-")) {
                break;
            }
            if (("-s".equals(opt)) || ("--no-src".equals(opt))) {
                decoder.setDecodeSources((short)0);
            } else if (("-d".equals(opt)) || ("--debug".equals(opt))) {
                decoder.setDebugMode(true);
            } else if (("-b".equals(opt)) || ("--no-debug-info".equals(opt))) {
                decoder.setBaksmaliDebugMode(false);
            } else if (("-t".equals(opt)) || ("--frame-tag".equals(opt))) {
                i++;

                decoder.setFrameworkTag(args[i]);
            } else if (("-f".equals(opt)) || ("--force".equals(opt))) {
                decoder.setForceDelete(true);
            } else if (("-r".equals(opt)) || ("--no-res".equals(opt))) {
                decoder.setDecodeResources((short)256);
            } else if ("--keep-broken-res".equals(opt)) {
                decoder.setKeepBrokenResources(true);
            } else if ("--frame-path".equals(opt)) {
                i++;
                System.out.println("Using Framework Directory: " + args[i]);
                decoder.setFrameworkDir(args[i]);
            } else {

            }


       String outName = null;
        if (args.length == i + 2) {
            outName = args[(i + 1)];
        } else if (args.length == i + 1) {
            outName = args[i];
            outName = outName + ".out";

            outName = new File(outName).getName();
        } else {
            throw new InvalidArgsError();
        }
        File outDir = new File(outName);
        decoder.setOutDir(outDir);
        decoder.setApkFile(new File(args[i]));
        try
        {
            decoder.decode();
        } catch (OutDirExistsException ex) {
            System.out.println("Destination directory (" + outDir.getAbsolutePath() + ") " + "already exists. Use -f switch if you want to overwrite it.");

            System.exit(1);
        } catch (InFileNotFoundException ex) {
            System.out.println("Input file (" + args[i] + ") " + "was not found or was not readable.");

            System.exit(1);
        } catch (CantFindFrameworkResException ex) {
            System.out.println("Can't find framework resources for package of id: " + String.valueOf(ex.getPkgId()) + ". You must install proper " + "framework files, see project website for more info.");

            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Could not modify file. Please ensure you have permission.");

            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }*//*
    }

    public static Map<String,String> getAndroidManifestUsePermission(String apkPath) throws IOException {
        String outName = null;
        try {
            outName = ApkUtils.cmdDecode(apkPath);
        } catch (AndrolibException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }


        Map<String,String> usePermissionMap = new HashMap<String,String>();

        Document document = XmlUtils.getDocument(outName+File.separator+"AndroidManifest.xml");
        Element elements = document.getRootElement();

        String workPath = ApkUtils.class.getClassLoader().getResource("").getPath();
        System.out.println(workPath);
        Properties properties = PptUtils.load(workPath+File.separator+"android.properties", true);
        for(Iterator<Element> iterator = elements.elementIterator("uses-permission");iterator.hasNext();){
            Element element = iterator.next();
            *//*
            Attribute androidAameAttr = element.attribute("android:name");// 获取<line>元素的属性
            String permission = androidAameAttr.getValue();
            *//*
            String value = element.attributeValue("name");
            //System.out.println("============"+value+"==============");
            if(value.startsWith("android.permission")){
                String describe = properties.getProperty(element.attributeValue("name").substring(19));
                if(describe != null){
                    //System.out.println(value+":"+describe);
                    usePermissionMap.put(value,describe);
                }
            }
        }
        return usePermissionMap;
    }

    public static void main(String[] args) throws AndrolibException, IllegalArgumentException, IOException {
        //String outName = ApkUtils.cmdDecode("d:/apk/weixin540android480.apk");
        //System.out.println(outName);
        Map<String,String> permission = getAndroidManifestUsePermission("d:/apk/weixin540android480.apk");
        for(String key:permission.keySet()){
            System.out.println(key+"  "+permission.get(key));
        }
    }

*/
}

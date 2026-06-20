public static void installApk(Context context, String apkPath) {
    if (apkFile==null || !apkFile.exists()) {
        Toast.makeText(context, "安装包不存在", Toast.LENGTH_SHORT).show();
        return;
    }


    Uri apkUri;
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //安卓7.0及以上版本需要使用FileProvider来获取Uri
        apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", apkFile);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    } else {
        安卓7.0以下版本直接使用Uri.fromFile获取Uri
        apkUri = Uri.fromFile(apkFile);
    }

    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
    context.startActivity(intent);
}
package com.hebeishida.common;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id ="2016101500693153";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCQ2BSptPQbwL22+7mvyQUI6NMzXs2VLAxxcxBvKv1YixGC670sQTzBWtx8Cxk1+zWCwgXOxS998dppJrxGHShtjt2C70Z75l9bFS88HnIRzFKgyeUTcarzxlOJ+rTHHWt7Gzyh93zvoTaAGfWOCgwvMN7J1wS6Tf7QR5vlCb5WGrcUD0tPhbYdsV2/u6qIB69TOjolGd2zWYl3js/y+MvBTfaTLbYoB0NvM2NqCWQpp4a/EiFGB60g88U98gohUSlQnRXEptNojoKfeUGclkeGYGNWhwqi2ZgiV16yOuYoWNqX0ypwA3H/p7oio9j5OLB3+nr4Djld7g1nr2Xr4oHpAgMBAAECggEAOsur35O3v4hGun+GSaf05j/0c5bZJuOBjY/irO2rd4TEL/vXh55XtjlW5PdwXWxCtXhi+SMr+xjwytxRcrilT4u9uUe9uc+zB3sa+HF3ZmglZmVuGq16coggIxNkzKrRlWBiBXE8At3TDL784ZKMjZ9t4Erq/WFpvUxEKP4CiFqlQoJXd3LkUuqjCIA06J6JKnDC5etyUBz7Ue8FeFZ3Q1498H2DB3EKpB2Fsl+gmoWZNqLkU8qTJdOLGyQyA495/HgNY5e8+O/oy7+mJWPl+nTcocDeA99HsCEVJrYOXizoJ5aqYHjpW1gcZIoMekJ9I5LKiBXuUGcmwdyLF712gQKBgQDLdpHT5w5i27Tk29R4rZ60fzE+lNs9AcnZTyNQa5gTGeQm1myi6KAiTWWP19RUdJpV+djCXzsMXtt1UHLzxI57o4IHA4ydoNpG5V4InDuIX/Olv1h2SLQn0d8PL9sGzr2nf9z7/3T1g7N0FgfdDG/D6DLm6lU26QW5PtFAoQe+mQKBgQC2PqPpE6Bjfln9ILpFT/+hKV9RFf21eU1y8mwP4S4WzA/36nMJmKVIjk0tv7/rzW8nqjExCCnCe5wSFXocXBxqwjBzbHUc5fDInFGmtmk+tXZTACUBwhwqmgBWmAFEqwiA97KSGn7cNgnth+d8XB0GaImsN9QQyDWKvbQTEFJ/0QKBgHcf9eOufZLN9kyklPMPHEOr0MZGW1KN0IOmPRPh2wkb+KPwmWyMbO2IiTU99bh5CL/b59ht72GfzDgUoSkuZQnowsGeh17nBcXKl1LNPmHrY/kgXgfrK2fBDiuTJlcHBq2iRF1axTzWHmSSXYpDtNBsDBl9C+V5a3t5VwTTA3jJAoGAUpQPsY0zH+Ig5JHv1XhqXJxilFtimthcP7mZPZjLSYktsyiI99AzFK1mS1SXokF0HIUBxTXttjJMheU1b6RNPBLB4YtDhdSrRjuOw/vjtIaFFxb5rdJG8QbXIGXALV1SfR2/zHQ1B5N07Cyot7EFdeHogCDL9s7BdByBfCHpQAECgYBB30eARukqjUIn0hI2/8eCbnuGkZON0MTb2fUp3z0gNd9+aVk7AIjFsGjX6jDHvZDrCIB2nQs5eQ5SyUpTCWl07vLVjUnlI8R2sltOCZJC1hr9kY2hhgoDAytyn0ZKHNXk1NjYdXWtUTfmTaE4X0iHUhAHb2X72kpPlc9zWLnbDg==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyr73H6Em9VRV9ctHvznwuFlbN2+l8YkeCyJ4+x5yft7yVuxKYss8XXuMZTnjthTt6u2pM12XmuvSYOxxZySPnZDMcOvPX/anXI9IPO3dz/BpVNm2KKGxlem1oxMcHnIZBOomxgvW+vdlkXuhan9HiA9t8AwQ/ZWvFTB0zWFpNDyU+byAxB0qRvoAajJL7/pSpsTNqD3yy3zQMmYf7Fwsrw27rEzJEdxEib6QMERXN4ps20mqDHxw2XvPJ+VmG0lmtr7VSclnAn2N9el6URLJBaQueR84cy+kK8HnEHnAQicaaS7VWliUZl67CHxpHod0qrTPFSgZCi8n7/2ojqT79QIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "https://openapi.alipay.com/gateway.do ";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


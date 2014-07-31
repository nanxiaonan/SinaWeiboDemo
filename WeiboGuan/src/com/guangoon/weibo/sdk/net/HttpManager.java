package com.guangoon.weibo.sdk.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.guangoon.weibo.sdk.exception.WeiboException;
import com.guangoon.weibo.sdk.exception.WeiboHttpException;

import android.graphics.Bitmap;

public class HttpManager {
	private static final String BOUNDARY = "";
	private static final String MP_BOUNDARY = "--" + BOUNDARY;
	private static final String END_MP_BOUNDARY = "--" + BOUNDARY + "--";
	private static final String MULTIPART_FORM_DATA = "multipart/form-data";
	private static final String HTTP_METHOD_POST = "POST";
	private static final String HTTP_METHOD_GET = "GET";
	private static final int CONNECTION_TIMEOUT = 5000;
	private static final int SOCKET_TIMEOUT = 20000;
	private static final int BUFFER_SIZE = 8192;
	private static SSLSocketFactory sSSLSocketFactory;

	public static String openUrl(String url, String method,
			WeiboParameters params) {
		HttpResponse response = requestHttpExecute(url, method, params);
		return readRsponse(response);
	}

	private static HttpClient getNewHttpClient() {
		try {
			HttpParams params = new BasicHttpParams();
			//设置TTTP版本，现在一般都是1.1
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, "UTF-8");

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", getSSLSocketFactory(), 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 20000);
			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
		}
		return new DefaultHttpClient();
	}

	private static SSLSocketFactory getSSLSocketFactory() {
		if (sSSLSocketFactory == null) {
			try {
				sSSLSocketFactory = SSLSocketFactory.getSocketFactory();
				/*
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				InputStream caInput = HttpManager.class
						.getResourceAsStream("cacert.cer");
				Certificate ca;
				try {
					ca = cf.generateCertificate(caInput);
				} finally {
					// Certificate ca;
					caInput.close();
				}
				String keyStoreType = KeyStore.getDefaultType();
				KeyStore keyStore = KeyStore.getInstance(keyStoreType);
				keyStore.load(null, null);
				keyStore.setCertificateEntry("ca", ca);

				sSSLSocketFactory = new SSLSocketFactory(keyStore);
				*/
			} catch (Exception e) {
				e.printStackTrace();

				sSSLSocketFactory = SSLSocketFactory.getSocketFactory();
			}
		}
		return sSSLSocketFactory;
	}

	private static HttpResponse requestHttpExecute(String url, String method,
			WeiboParameters params) {
		HttpResponse response = null;
		try {
			HttpClient client = getNewHttpClient();
			client.getParams().setParameter("http.route.default-proxy",
					NetStateManager.getAPN());

			HttpUriRequest request = null;
			ByteArrayOutputStream baos = null;
			if (method.equals("GET")) {
				url = url + "?" + params.encodeUrl();
				request = new HttpGet(url);
			} else if (method.equals("POST")) {
				HttpPost post = new HttpPost(url);
				request = post;

				baos = new ByteArrayOutputStream();
				if (params.hasBinaryData()) {
					post.setHeader("Content-Type",
							"multipart/form-data; boundary=" + BOUNDARY);
					buildParams(baos, params);
				} else {
					Object value = params.get("content-type");
					if ((value != null) && ((value instanceof String))) {
						params.remove("content-type");
						post.setHeader("Content-Type", (String) value);
					} else {
						post.setHeader("Content-Type",
								"application/x-www-form-urlencoded");
					}
					String postParam = params.encodeUrl();
					baos.write(postParam.getBytes("UTF-8"));
				}
				post.setEntity(new ByteArrayEntity(baos.toByteArray()));
				baos.close();
			} else if (method.equals("DELETE")) {
				request = new HttpDelete(url);
			}
			response = client.execute(request);
			StatusLine status = response.getStatusLine();
			int statusCode = status.getStatusCode();
			if (statusCode != 200) {
				String result = readRsponse(response);
				throw new WeiboHttpException(result, statusCode);
			}
		} catch (IOException e) {
			throw new WeiboException(e);
		}
		return response;
	}

	private static void buildParams(OutputStream baos, WeiboParameters params)
			throws WeiboException {
		try {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				Object value = params.get(key);
				if ((value instanceof String)) {
					StringBuilder sb = new StringBuilder(100);
					sb.setLength(0);
					sb.append(MP_BOUNDARY).append("\r\n");
					sb.append("content-disposition: form-data; name=\"")
							.append(key).append("\"\r\n\r\n");
					sb.append(params.get(key)).append("\r\n");

					baos.write(sb.toString().getBytes());
				}
			}
			for (String key : keys) {
				Object value = params.get(key);
				if ((value instanceof Bitmap)) {
					StringBuilder sb = new StringBuilder();
					sb.append(MP_BOUNDARY).append("\r\n");
					sb.append("content-disposition: form-data; name=\"")
							.append(key).append("\"; filename=\"file\"\r\n");
					sb.append("Content-Type: application/octet-stream; charset=utf-8\r\n\r\n");
					baos.write(sb.toString().getBytes());

					Bitmap bmp = (Bitmap) value;
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
					byte[] bytes = stream.toByteArray();

					baos.write(bytes);
					baos.write("\r\n".getBytes());
				} else if ((value instanceof ByteArrayOutputStream)) {
					StringBuilder sb = new StringBuilder();
					sb.append(MP_BOUNDARY).append("\r\n");
					sb.append("content-disposition: form-data; name=\"")
							.append(key).append("\"; filename=\"file\"\r\n");
					sb.append("Content-Type: application/octet-stream; charset=utf-8\r\n\r\n");
					baos.write(sb.toString().getBytes());

					ByteArrayOutputStream stream = (ByteArrayOutputStream) value;
					baos.write(stream.toByteArray());
					baos.write("\r\n".getBytes());
					stream.close();
				}
			}
			baos.write(("\r\n" + END_MP_BOUNDARY).getBytes());
		} catch (IOException e) {
			throw new WeiboException(e);
		}
	}

	private static String readRsponse(HttpResponse response)
			throws WeiboException {
		if (response == null) {
			return null;
		}
		HttpEntity entity = response.getEntity();
		InputStream inputStream = null;
		ByteArrayOutputStream content = new ByteArrayOutputStream();
		try {
			inputStream = entity.getContent();
			Header header = response.getFirstHeader("Content-Encoding");
			if ((header != null)
					&& (header.getValue().toLowerCase().indexOf("gzip") > -1)) {
				inputStream = new GZIPInputStream(inputStream);
			}
			int readBytes = 0;
			byte[] buffer = new byte[8192];
			while ((readBytes = inputStream.read(buffer)) != -1) {
				content.write(buffer, 0, readBytes);
			}
			return new String(content.toByteArray(), "UTF-8");
		} catch (IOException e) {
			throw new WeiboException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

/*  1:   */ package com.guangoon.weibo.sdk.exception;
/*  2:   */ 
/*  3:   */ public class WeiboHttpException
/*  4:   */   extends WeiboException
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 1L;
/*  7:   */   private final int mStatusCode;
/*  8:   */   
/*  9:   */   public WeiboHttpException(String message, int statusCode)
/* 10:   */   {
/* 11:39 */     super(message);
/* 12:40 */     this.mStatusCode = statusCode;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public int getStatusCode()
/* 16:   */   {
/* 17:49 */     return this.mStatusCode;
/* 18:   */   }
/* 19:   */ }


/* Location:           F:\android\weibo_android_sdk-master\weibo_android_sdk-master\weibosdkcore.jar
 * Qualified Name:     com.sina.weibo.sdk.exception.WeiboHttpException
 * JD-Core Version:    0.7.0.1
 */
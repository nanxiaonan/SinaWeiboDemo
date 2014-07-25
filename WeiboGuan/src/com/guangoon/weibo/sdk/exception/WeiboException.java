/*  1:   */ package com.guangoon.weibo.sdk.exception;
/*  2:   */ 
/*  3:   */ public class WeiboException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 475022994858770424L;
/*  7:   */   
/*  8:   */   public WeiboException() {}
/*  9:   */   
/* 10:   */   public WeiboException(String message)
/* 11:   */   {
/* 12:45 */     super(message);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public WeiboException(String detailMessage, Throwable throwable)
/* 16:   */   {
/* 17:55 */     super(detailMessage, throwable);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public WeiboException(Throwable throwable)
/* 21:   */   {
/* 22:64 */     super(throwable);
/* 23:   */   }
/* 24:   */ }


/* Location:           F:\android\weibo_android_sdk-master\weibo_android_sdk-master\weibosdkcore.jar
 * Qualified Name:     com.sina.weibo.sdk.exception.WeiboException
 * JD-Core Version:    0.7.0.1
 */
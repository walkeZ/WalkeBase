package com.hui.huiheight.first.retrofit2;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class AdvData {

   /**
    * data : [{"index":"2241","imgurl":"http://p.mdbimg.com/slide_201607_5784e84388ff8txqrae.jpg","link":"http://www.meidebi.com/h-1236599.html","title":"prime福利！服饰箱包，一年一度的7折来了！","styles":"","description":"","starttime":"1468327980","endtime":"1468393140","sort":"15","status":"1","type":"1","updatetime":"1468328027","imgUrl":"http://p.mdbimg.com/slide_201607_5784e84388ff8txqrae.jpg"},{"index":"2233","imgurl":"http://p.mdbimg.com/slide_201607_578318843568domzvwj.jpg","link":"http://www.meidebi.com/g-1233600.html","title":"周年庆全场低至2折","styles":"","description":"","starttime":"1468209300","endtime":"1469980740","sort":"14","status":"1","type":"1","updatetime":"1468209303","imgUrl":"http://p.mdbimg.com/slide_201607_578318843568domzvwj.jpg"},{"index":"2231","imgurl":"http://p.mdbimg.com/slide_201607_57830f5f5199buvzitc.jpg","link":"http://www.meidebi.com/g-1234094.html","title":"各国亚马逊PrimeDay会员日活动！你准备好没有！","styles":"","description":"","starttime":"1468206900","endtime":"1468396740","sort":"13","status":"1","type":"1","updatetime":"1468206962","imgUrl":"http://p.mdbimg.com/slide_201607_57830f5f5199buvzitc.jpg"},{"index":"2218","imgurl":"http://p.mdbimg.com/slide_201607_5783074e195c3gckmjo.jpg","link":"http://www.meidebi.com/g-1232618.html","title":"家居家装 最高455-255","styles":"","description":"","starttime":"1468200480","endtime":"1468684740","sort":"12","status":"1","type":"1","updatetime":"1468204880","imgUrl":"http://p.mdbimg.com/slide_201607_5783074e195c3gckmjo.jpg"}]
    * info : GET_DATA_SUCCESS
    * status : 1
    */

   private String info;
   private int status;
   /**
    * index : 2241
    * imgurl : http://p.mdbimg.com/slide_201607_5784e84388ff8txqrae.jpg
    * link : http://www.meidebi.com/h-1236599.html
    * title : prime福利！服饰箱包，一年一度的7折来了！
    * styles :
    * description :
    * starttime : 1468327980
    * endtime : 1468393140
    * sort : 15
    * status : 1
    * type : 1
    * updatetime : 1468328027
    * imgUrl : http://p.mdbimg.com/slide_201607_5784e84388ff8txqrae.jpg
    */

   private List<DataBean> data;

   public String getInfo() {
      return info;
   }

   public void setInfo(String info) {
      this.info = info;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public List<DataBean> getData() {
      return data;
   }

   public void setData(List<DataBean> data) {
      this.data = data;
   }

   public static class DataBean {
      private String id;
      private String imgurl;
      private String link;
      private String title;
      private String styles;
      private String description;
      private String starttime;
      private String endtime;
      private String sort;
      private String status;
      private String type;
      private String updatetime;
      private String imgUrl;

      public String getId() {
         return id;
      }

      public void setId(String id) {
         this.id = id;
      }

      public String getImgurl() {
         return imgurl;
      }

      public void setImgurl(String imgurl) {
         this.imgurl = imgurl;
      }

      public String getLink() {
         return link;
      }

      public void setLink(String link) {
         this.link = link;
      }

      public String getTitle() {
         return title;
      }

      public void setTitle(String title) {
         this.title = title;
      }

      public String getStyles() {
         return styles;
      }

      public void setStyles(String styles) {
         this.styles = styles;
      }

      public String getDescription() {
         return description;
      }

      public void setDescription(String description) {
         this.description = description;
      }

      public String getStarttime() {
         return starttime;
      }

      public void setStarttime(String starttime) {
         this.starttime = starttime;
      }

      public String getEndtime() {
         return endtime;
      }

      public void setEndtime(String endtime) {
         this.endtime = endtime;
      }

      public String getSort() {
         return sort;
      }

      public void setSort(String sort) {
         this.sort = sort;
      }

      public String getStatus() {
         return status;
      }

      public void setStatus(String status) {
         this.status = status;
      }

      public String getType() {
         return type;
      }

      public void setType(String type) {
         this.type = type;
      }

      public String getUpdatetime() {
         return updatetime;
      }

      public void setUpdatetime(String updatetime) {
         this.updatetime = updatetime;
      }

      public String getImgUrl() {
         return imgUrl;
      }

      public void setImgUrl(String imgUrl) {
         this.imgUrl = imgUrl;
      }

      @Override
      public String toString() {
         return "title='" + title ;
      }
   }
}

package suts.desigenpattens.learnnote.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sutingshuai on 2019-08-29
 * Describe:
 */
public class BlogResponse {
    @Expose
    @SerializedName("status_code")
    private String statusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<Blog> data;

    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public List<Blog> getData() { return data; }
    public void setData(List<Blog> data) { this.data = data; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogResponse that = (BlogResponse) o;

        if (statusCode != null ? !statusCode.equals(that.statusCode) : that.statusCode != null)
            return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return data != null ? data.equals(that.data) : that.data == null;
    }

    @Override
    public int hashCode() {
        int result = statusCode != null ? statusCode.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public static class Blog{
        @Expose
        @SerializedName("blog_url")
        private String blogUrl;

        @Expose
        @SerializedName("img_url")
        private String coverImgUrl;

        @Expose
        @SerializedName("title")
        private String title;

        @Expose
        @SerializedName("description")
        private String description;

        @Expose
        @SerializedName("author")
        private String author;

        @Expose
        @SerializedName("published_at")
        private String date;

        public String getBlogUrl() { return blogUrl; }
        public void setBlogUrl(String blogUrl) { this.blogUrl = blogUrl; }
        public String getCoverImgUrl() { return coverImgUrl; }
        public void setCoverImgUrl(String coverImgUrl) { this.coverImgUrl = coverImgUrl; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Blog blog = (Blog) o;

            if (blogUrl != null ? !blogUrl.equals(blog.blogUrl) : blog.blogUrl != null)
                return false;
            if (coverImgUrl != null ? !coverImgUrl.equals(blog.coverImgUrl) : blog.coverImgUrl != null)
                return false;
            if (title != null ? !title.equals(blog.title) : blog.title != null) return false;
            if (description != null ? !description.equals(blog.description) : blog.description != null)
                return false;
            if (author != null ? !author.equals(blog.author) : blog.author != null) return false;
            return date != null ? date.equals(blog.date) : blog.date == null;
        }

        @Override
        public int hashCode() {
            int result = blogUrl != null ? blogUrl.hashCode() : 0;
            result = 31 * result + (coverImgUrl != null ? coverImgUrl.hashCode() : 0);
            result = 31 * result + (title != null ? title.hashCode() : 0);
            result = 31 * result + (description != null ? description.hashCode() : 0);
            result = 31 * result + (author != null ? author.hashCode() : 0);
            result = 31 * result + (date != null ? date.hashCode() : 0);
            return result;
        }
    }
}

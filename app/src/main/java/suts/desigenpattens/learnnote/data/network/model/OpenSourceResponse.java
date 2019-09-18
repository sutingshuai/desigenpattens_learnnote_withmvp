package suts.desigenpattens.learnnote.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sutingshuai on 2019-08-29
 * Describe:
 */
public class OpenSourceResponse {
    @Expose
    @SerializedName("status_code")
    private String statusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<Repo> data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public List<Repo> getData() { return data; }
    public void setData(List<Repo> data) { this.data = data; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpenSourceResponse that = (OpenSourceResponse) o;

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

    public static class Repo{
        @Expose
        @SerializedName("project_url")
        private String projectUrl;

        @Expose
        @SerializedName("img_url")
        private String coverImgUrl;

        @Expose
        @SerializedName("title")
        private String title;

        @Expose
        @SerializedName("description")
        private String description;

        public String getProjectUrl() { return projectUrl; }
        public void setProjectUrl(String projectUrl) { this.projectUrl = projectUrl; }
        public String getCoverImgUrl() { return coverImgUrl; }
        public void setCoverImgUrl(String coverImgUrl) { this.coverImgUrl = coverImgUrl; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Repo repo = (Repo) o;

            if (projectUrl != null ? !projectUrl.equals(repo.projectUrl) : repo.projectUrl != null)
                return false;
            if (coverImgUrl != null ? !coverImgUrl.equals(repo.coverImgUrl) : repo.coverImgUrl != null)
                return false;
            if (title != null ? !title.equals(repo.title) : repo.title != null) return false;
            return description != null ? description.equals(repo.description) : repo.description == null;
        }

        @Override
        public int hashCode() {
            int result = projectUrl != null ? projectUrl.hashCode() : 0;
            result = 31 * result + (coverImgUrl != null ? coverImgUrl.hashCode() : 0);
            result = 31 * result + (title != null ? title.hashCode() : 0);
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }
    }
}

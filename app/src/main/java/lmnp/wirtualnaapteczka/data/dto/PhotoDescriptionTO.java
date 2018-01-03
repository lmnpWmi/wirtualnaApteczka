package lmnp.wirtualnaapteczka.data.dto;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

public class PhotoDescriptionTO implements Serializable {
    private static final long serialVersionUID = 1252404752254819870L;

    private String fullSizePhotoUri;
    private String smallSizePhotoUri;
    private List<String> oldPhotoUrisToDelete;

    public PhotoDescriptionTO(String fullSizePhotoUri, String smallSizePhotoUri) {
        this.fullSizePhotoUri = fullSizePhotoUri;
        this.smallSizePhotoUri = smallSizePhotoUri;
    }

    public PhotoDescriptionTO() {
    }

    public boolean isEmpty() {
        boolean isEmpty = TextUtils.isEmpty(fullSizePhotoUri) || TextUtils.isEmpty(smallSizePhotoUri);
        return isEmpty;
    }

    public String getFullSizePhotoUri() {
        return fullSizePhotoUri;
    }

    public void setFullSizePhotoUri(String fullSizePhotoUri) {
        this.fullSizePhotoUri = fullSizePhotoUri;
    }

    public String getSmallSizePhotoUri() {
        return smallSizePhotoUri;
    }

    public void setSmallSizePhotoUri(String smallSizePhotoUri) {
        this.smallSizePhotoUri = smallSizePhotoUri;
    }

    public List<String> getOldPhotoUrisToDelete() {
        return oldPhotoUrisToDelete;
    }

    public void setOldPhotoUrisToDelete(List<String> oldPhotoUrisToDelete) {
        this.oldPhotoUrisToDelete = oldPhotoUrisToDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhotoDescriptionTO that = (PhotoDescriptionTO) o;

        if (fullSizePhotoUri != null ? !fullSizePhotoUri.equals(that.fullSizePhotoUri) : that.fullSizePhotoUri != null)
            return false;
        if (smallSizePhotoUri != null ? !smallSizePhotoUri.equals(that.smallSizePhotoUri) : that.smallSizePhotoUri != null)
            return false;
        return oldPhotoUrisToDelete != null ? oldPhotoUrisToDelete.equals(that.oldPhotoUrisToDelete) : that.oldPhotoUrisToDelete == null;
    }

    @Override
    public int hashCode() {
        int result = fullSizePhotoUri != null ? fullSizePhotoUri.hashCode() : 0;
        result = 31 * result + (smallSizePhotoUri != null ? smallSizePhotoUri.hashCode() : 0);
        result = 31 * result + (oldPhotoUrisToDelete != null ? oldPhotoUrisToDelete.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhotoDescriptionTO{" +
                "fullSizePhotoUri='" + fullSizePhotoUri + '\'' +
                ", smallSizePhotoUri='" + smallSizePhotoUri + '\'' +
                ", oldPhotoUrisToDelete=" + oldPhotoUrisToDelete +
                '}';
    }
}

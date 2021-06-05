package edu.neu.numad21su_osmansubasi;

public class LinkCard implements LinkClickListener{
    private  String linkName;
    private  String linkUrl;

    public LinkCard(String linkName, String linkUrl) {
        this.linkName = linkName;
        this.linkUrl = linkUrl;
    }

    public String getLinkName() {
        return linkName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }
    @Override
    public void onItemClick(String url) {

    }
}

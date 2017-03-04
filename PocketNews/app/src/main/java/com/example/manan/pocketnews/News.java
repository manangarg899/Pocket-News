package com.example.manan.pocketnews;

import java.util.HashMap;

import static android.R.attr.author;

/**
 * Created by Manan on 02-03-2017.
 */

public class News {

    private String mHeadline;

    private String mContent;

    private String mAuthor;

    private String mUrl;

    private String mImageUrl;

    public News(String headline, String standFirst, String byLine, String shortUrl, String thumbnailUrl) {
        mHeadline = headline;

        mContent = standFirst;

        mAuthor = byLine;

        mUrl = shortUrl;

        mImageUrl = thumbnailUrl;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public String getContent() {
        return mContent;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}

package com.crio.shorturl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class XUrlImpl implements XUrl {

    private Map<String, ArrayList<String>> longToShortUrlMap = new HashMap<>();
    private Map<String, String> shortToLongUrlMap = new HashMap<>();

    @Override
    public String registerNewUrl(String longUrl) {
        
        // Check if the given longUrl is already present or not
        if(this.longToShortUrlMap.containsKey(longUrl)) {
            // If present, then return stored shortUrl.
            return this.longToShortUrlMap.get(longUrl).get(0);
        }

        String uniqueKeyString = this.generateRandomString();
        uniqueKeyString = "http://short.url/" + uniqueKeyString;
        ArrayList<String> arr = new ArrayList<>();
        arr.add(uniqueKeyString);
        arr.add("0");

        this.longToShortUrlMap.put(longUrl, arr);
        this.shortToLongUrlMap.put(uniqueKeyString, longUrl);
        
        return uniqueKeyString;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        
        if(this.shortToLongUrlMap.containsKey(shortUrl))
            return null;

        ArrayList<String> arr = new ArrayList<>();
        arr.add(shortUrl);
        arr.add("0");

        this.longToShortUrlMap.put(longUrl, arr);
        this.shortToLongUrlMap.put(shortUrl, longUrl);

        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {

        if(!shortToLongUrlMap.containsKey(shortUrl)) {
            return null;
        }

        String longUrl = shortToLongUrlMap.get(shortUrl);
        int hitCount = Integer.valueOf(this.longToShortUrlMap.get(longUrl).get(1));
        ++hitCount;

        this.longToShortUrlMap.get(longUrl).add(1, hitCount + "");

        return shortToLongUrlMap.get(shortUrl);
    }

    @Override
    public Integer getHitCount(String longUrl) {
        if(!this.longToShortUrlMap.containsKey(longUrl))
            return 0;

        return Integer.valueOf(this.longToShortUrlMap.get(longUrl).get(1));
    }

    @Override
    public String delete(String longUrl) {
        String shortUrl = this.longToShortUrlMap.get(longUrl).get(0);

        this.longToShortUrlMap.remove(longUrl);
        this.shortToLongUrlMap.remove(shortUrl);

        return null;
    }

    private String generateRandomString() {

        final String uniqueAlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        final int uniqueKeySize = 9;

        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 1; i <= uniqueKeySize; i++) {
            sb.append(
                uniqueAlphaNumericString.charAt(
                    random.nextInt(
                        uniqueAlphaNumericString.length()
                    )
                )
            );
        }
    
        return sb.toString();
    }
    
}
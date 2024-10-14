package com.ljx213101212.search_engine.config;

public final class Constants {

  public static class Suggest {
//    public static final String SUGGEST_TITLE = "title_suggest";
//    public static final String SUGGEST_TITLE_NAME = "title-suggest";
//    public static final String DID_YOU_MEAN = "did_you_mean";
      public static final String SUGGEST_BOOK = "book_suggest";
  }


  public static class Aggregations {
    public static final String FACET_TITLE_NAME = "agg_title";
    public static final String FACET_AUTHOR_NAME = "agg_author";
    public static final String FACET_LANGUAGE_NAME = "agg_language";


    //Make sure no typos compares to model attributeslan
    public static final String FACET_TITLE = "title.keyword";
    public static final String FACET_AUTHOR = "authors.keyword";
    public static final String FACET_LANGUAGE = "language.keyword";
  }
}
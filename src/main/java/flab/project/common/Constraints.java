package flab.project.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constraints {

    public static final int MAX_LENGTH_OF_POST_CONTENTS = 2000;
    public static final int MAX_LENGTH_OF_POLL_SUBJECT = 50;
    public static final int NUMBER_LIMIT_OF_SOCIAL_ACCOUNTS = 3;
    public static final int MAX_LENGTH_DEBATE_POST_OPTION_CONTENT = 35;
    public static final int MAX_LENGTH_POLL_POST_OPTION_CONTENT = 15;
    public static final int FIXED_SIZE_OF_DEBATE_POST_OPTIONS = 2;
    public static final int MIN_SIZE_OF_POLL_POST_OPTION_COUNT = 2;
    public static final int MAX_SIZE_OF_POLL_POST_OPTION_COUNT = 5;
    public static final int MAX_SIZE_OF_POST_HASHTAGS = 5;
    public static final int MAX_LENGTH_OF_HASHTAGS = 15;
}
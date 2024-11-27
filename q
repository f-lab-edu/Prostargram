[33mcommit e551c432c783fc30b1b355cb69b0dc7c84f85d52[m[33m ([m[1;36mHEAD -> [m[1;32mfeat/#221[m[33m)[m
Author: J-MU <alsdnrdl001@gmail.com>
Date:   Wed Nov 27 22:12:44 2024 +0900

    [Fix] 피드가 없을 경우 예외 처리

[33mcommit b9c702278eca6745285ada46121f0b8ee9f4d124[m
Author: J-MU <alsdnrdl001@gmail.com>
Date:   Wed Nov 20 17:59:05 2024 +0900

    좋아요 POST테이블의 like_count에도 표현

[33mcommit 6fe871d54a53c1bc54f3a43c3e8994cdeadef56e[m
Author: J-MU <alsdnrdl001@gmail.com>
Date:   Wed Nov 20 17:54:51 2024 +0900

    피드 조회 API isFollow,isLike,likeCount와 같은 데이터 동기화

[33mcommit c620e8e1d3e7ae006c6d1e975830a10a4e23635b[m
Author: J-MU <alsdnrdl001@gmail.com>
Date:   Tue Nov 19 15:42:05 2024 +0900

    피드 조회 API hasNext추가

[33mcommit c4aa07ac7f5faee118c034f7e79592d78d58742f[m[33m ([m[1;31morigin/dev[m[33m, [m[1;31morigin/HEAD[m[33m, [m[1;32mdev[m[33m)[m
Merge: 77de6d2 4c2bfc1
Author: Eunbi Lee <pask2202@gmail.com>
Date:   Sun Nov 10 22:16:55 2024 +0900

    Merge pull request #220 from f-lab-edu/feat/#219
    
    [#219] CommentController 리팩토링

[33mcommit 4c2bfc1b6a854bf9eaca3eac3bc0190048c58d16[m[33m ([m[1;31morigin/feat/#219[m[33m)[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 21:56:33 2024 +0900

    [refactor] CommentController getComments limit description 추가

[33mcommit 95e27765c7784bf572e5a5af540916504d5739d9[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 21:54:54 2024 +0900

    [refactor] CommentController getComments 대댓글 조회 description 추가

[33mcommit 7fa4d040beaec43a323eeacb9929ed53fd87fab4[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 21:53:24 2024 +0900

    [refactor] CommentController content 예시 수정

[33mcommit 77de6d2331f9abe8efd220bcaf87e1af8ffd860f[m
Merge: be7b49e 23e6385
Author: Eunbi Lee <pask2202@gmail.com>
Date:   Sun Nov 10 21:30:34 2024 +0900

    Merge pull request #218 from f-lab-edu/feat/#217
    
    [#217] FollowController 리팩토링

[33mcommit 23e6385d3de097decea8eb7c8b1fe6997cc66106[m[33m ([m[1;31morigin/feat/#217[m[33m)[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 21:00:40 2024 +0900

    [refactor] follow-mapper 사용되지 않는 findAllFollowerIds 삭제

[33mcommit cdd5c6055c64b40700d9b747218bcfe2c41cd16b[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 21:00:21 2024 +0900

    [refactor] FollowController 404 case 삭제

[33mcommit be7b49ef3e00d5646c895e06fa10fbc44f802d93[m
Merge: a151ca5 6894c29
Author: Eunbi Lee <pask2202@gmail.com>
Date:   Sun Nov 10 19:47:21 2024 +0900

    Merge pull request #216 from f-lab-edu/feat/#215
    
    [#215] POST Http status code 201 수정

[33mcommit 6894c29ed074c87e3ba4cce52519ec5649b635b0[m[33m ([m[1;31morigin/feat/#215[m[33m)[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 17:49:24 2024 +0900

    [refactor] VoteController POST case 201 수정

[33mcommit 727e5e66caf12932ac8b0cde7b489ea7a9e3fdde[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 17:42:12 2024 +0900

    [refactor] SocialAccountController POST case 201 수정

[33mcommit 3b030a8f5f4f4609f888a1d1a295e520246a56a7[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 17:42:03 2024 +0900

    [refactor] PostLikeController POST case 201 수정

[33mcommit 6962db817acf7afb3d1c3c80230cf4db7f0e08ad[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 17:41:52 2024 +0900

    [refactor] PollPostController POST case 201 수정

[33mcommit 3990c7b6f2a151fe25294dff83e0babfeb527781[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 17:41:43 2024 +0900

    [refactor] InterestController POST case 201 수정

[33mcommit 0b83a52c699ea41fceb393ea01b5bcd755ec3c04[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 17:41:29 2024 +0900

    [refactor] FollowController POST case 201 수정

[33mcommit 6e8e835ca49291005037e5e94a2e23b9b7954074[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 17:40:43 2024 +0900

    [refactor] DebatePostController POST case 201 및 깨지는 result 수정

[33mcommit 3056cf531290b2a7a759b3f1e3951d148cebe806[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 17:14:13 2024 +0900

    [refactor] CommentLikeController POST case 201 수정

[33mcommit ad13abde43f99cc41873944f23a3dc941ea0c838[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 17:13:55 2024 +0900

    [refactor] CommentController POST case 201 수정

[33mcommit 07ecd956e23e2c1b05ff5492b18136562336a0c6[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 17:13:34 2024 +0900

    [refactor] BasicPostController POST case 201 수정

[33mcommit a151ca52009e9f8d3b7aea656f0bb72f2ed038d4[m
Merge: ad481b5 f294674
Author: Eunbi Lee <pask2202@gmail.com>
Date:   Sun Nov 10 16:43:07 2024 +0900

    Merge pull request #214 from f-lab-edu/feat/#212
    
    [#212] SwaggerConfig 수정

[33mcommit f294674794bc39834d2ec616e253a6acc14a68da[m[33m ([m[1;31morigin/feat/#212[m[33m)[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:41:36 2024 +0900

    [chore] 라인 정리

[33mcommit 200c638643b15fce4472bd5c977091ce5eb853ea[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:39:06 2024 +0900

    [refactor] SwaggerConfig .addSecurityItem() 삭제

[33mcommit ad481b541603e223cf14e2fd14ae3694676532aa[m
Merge: 7467369 2cabb8c
Author: Eunbi Lee <pask2202@gmail.com>
Date:   Sun Nov 10 16:27:26 2024 +0900

    Merge pull request #213 from f-lab-edu/feat/#212
    
    [#212] Swagger @Operation security 삭제

[33mcommit 2cabb8c215a75b55d2b0640d4347998c8644e489[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:21:14 2024 +0900

    [refactor] VoteController @Operation security 삭제

[33mcommit 737aeae53568669641d5ca7ce34d2914d4f9c4dd[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:20:43 2024 +0900

    [refactor] VerificationController @Operation security, @Parameter 삭제 및 라인 정리

[33mcommit 6de096e53e702c4c73754e1beedcc21a917089fe[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:19:55 2024 +0900

    [refactor] UserController @Operation security 삭제

[33mcommit 1e916af9e0a3188b9b2454165bc0339e48073d38[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:18:43 2024 +0900

    [refactor] SocialAccountController @Operation security 삭제

[33mcommit 82f8bc617e82e52024d3e3cb479ae53f57bebadb[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:14:05 2024 +0900

    [refactor] SettingController @Operation security 및 @Parameter 삭제

[33mcommit 705a681554d58b53b86d0a433e85710d56710662[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:13:44 2024 +0900

    [refactor] PostLikeController @Operation security 삭제

[33mcommit d45924a9b0306e22119523769579c36ca647ed4e[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:13:23 2024 +0900

    [refactor] PostController @Operation security 삭제 및 라인 정리

[33mcommit 63426469e4670069fa632259e85126d59f108edd[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:13:11 2024 +0900

    [refactor] PollPostController @Operation security 삭제

[33mcommit 7352d15451fba570d98c0cfa4b260952e57a06a8[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:13:00 2024 +0900

    [refactor] InterestController @Operation security 삭제

[33mcommit 6ca2000a1bb60fb95c281e7619f732abb429b108[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:12:48 2024 +0900

    [refactor] FollowController @Operation security 삭제

[33mcommit 903cc688410a450178ba9bdf7ff20965f49d518b[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:12:34 2024 +0900

    [refactor] DebatePostController @Operation security 삭제

[33mcommit efd82ed79b60887528e11704f36c666f7c5a827b[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:10:16 2024 +0900

    [refactor] CommentLikeController @Operation security 삭제

[33mcommit 16b781438469ba855594d31408e31fbe0fbb4e2e[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:10:00 2024 +0900

    [refactor] CommentController @Operation security 삭제

[33mcommit 614a57428869f7f631ee772adb5bcc45d6bff839[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 10 16:09:49 2024 +0900

    [refactor] BasicPostController @Operation security 삭제

[33mcommit 7467369b29e3ff7882da7ab5cffc5584fd6bac56[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 3 19:08:54 2024 +0900

    [refactor] SettingController 누락된 import문 추가

[33mcommit 02f9714f793b7d9ae48f32d756824221de075a10[m
Merge: 99bed3a 3588a72
Author: Eunbi Lee <pask2202@gmail.com>
Date:   Sun Nov 3 19:03:13 2024 +0900

    Merge pull request #203 from f-lab-edu/feat/#197
    
    [#197] 전체 API에 대한 Swagger 작성 및 수정

[33mcommit 3588a727d2fd0b880d1a61e989514b3fdfa9ca7b[m[33m ([m[1;31morigin/feat/#197[m[33m)[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 3 19:02:56 2024 +0900

    [refactor] resolve conflict

[33mcommit dba895aaa3d7535d4dbdc6f6e49723af4ef16145[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 3 18:56:20 2024 +0900

    [refactor] 댓글 좋아요 API Swagger 수정

[33mcommit 4522e6144c0bd6c4701321f8da654eee80534945[m
Merge: a76ba8f 99bed3a
Author: Eunbi Lee <pask2202@gmail.com>
Date:   Sun Nov 3 18:53:58 2024 +0900

    Merge branch 'dev' into feat/#197

[33mcommit a76ba8f83492b2668e608c5e1f44b8f26bebac08[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sun Nov 3 18:45:00 2024 +0900

    [refactor] 댓글 조회 API Swagger (404, 500 case) 추가

[33mcommit 9eda6f554ffc19238c9b0bcefaf014cb04591aca[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Thu Oct 31 23:40:02 2024 +0900

    [refactor] 댓글 조회 API Swagger 수정

[33mcommit e548f9c7ea7142061b02d341127dc5aa8b886e42[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Thu Oct 31 23:39:14 2024 +0900

    [refactor] FollowController 관련 Swagger 수정

[33mcommit 99bed3a3cba70b4ed04288fef13c7148c8d42525[m
Author: MIN UK JUNG <alsdnrdl001@gmail.com>
Date:   Wed Oct 30 19:11:00 2024 +0900

    [Docs] swagger 작성 (#210)

[33mcommit e13b36bde0885d1092093e61656d68f82677ce82[m
Author: MIN UK JUNG <alsdnrdl001@gmail.com>
Date:   Wed Oct 30 15:11:03 2024 +0900

    [fix] swagger Authorization button 추가 (#208)

[33mcommit f97d9cfb844b99fe9cc8adeb6a823e00ebf60c07[m[33m ([m[1;32mrefactor/206[m[33m)[m
Author: MIN UK JUNG <alsdnrdl001@gmail.com>
Date:   Sun Oct 20 21:57:01 2024 +0900

    [feat] existsById 구현 (#205)

[33mcommit bb0b810f5da02855a3a06ab662c97fc1e18decb7[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:35:06 2024 +0900

    [feat] 투표 API Swagger 작성

[33mcommit f648a2f4c9cd2ad57658361d06c5c8694187859f[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:34:38 2024 +0900

    [refactor] 회원가입 API 대문 및 컨벤션에 따른 수정

[33mcommit b3000c2a032994690704c735bddd496b880d0450[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:34:08 2024 +0900

    [refactor] 닉네임 중복 API 컨벤션에 따른 수정

[33mcommit 52f0a97b6aa363b8ea36559fe9b83e61f1de688d[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:33:31 2024 +0900

    [feat] 프로필 API Swagger 작성

[33mcommit d53c6474dd6a5e6f98e557a504532ace24eafb2a[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:33:07 2024 +0900

    [feat] 테스트 API Swagger 작성

[33mcommit 65c4f9d1fe17396298ad914e249f361daace2aaa[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:32:13 2024 +0900

    [refactor] 소셜 계정 API Bearer Token 어노테이션 추가

[33mcommit 9fc4c700571c3a8034d24872c17e936a855363ea[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:32:08 2024 +0900

    [feat] 설정 API Swagger 작성

[33mcommit 2057e07431aa8088d7ebd517ac79aad02b39ee57[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:31:53 2024 +0900

    [refactor] 관심사 관련 ResponseEnum 추가

[33mcommit 0efb0e6d51c93b66c9488fb1266f6146085dafc3[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:31:35 2024 +0900

    [refactor] RabbitMQ Consumer 레포를 분리함에 따라 Controller 삭제

[33mcommit bfd73fe64d4793e95689c1d1b94bbd3d799cd085[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:31:08 2024 +0900

    [feat] 게시물 좋아요 API Swagger 작성

[33mcommit 4f98509d36f8c5a3ba3c86320a0a3fbc9405a4c5[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:30:24 2024 +0900

    [feat] 관심사 API Swagger 작성

[33mcommit 512892ea5afb6e7712299ad7e48a75b9748b3d19[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:29:17 2024 +0900

    [refactor] 팔로워/팔로잉 관련 API Bearer Token 어노테이션 및 조회 결과 추가

[33mcommit b2b3a6bd54fa228df5781d00a20e8b3a2ac33157[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:28:30 2024 +0900

    [refactor] 댓글 좋아요 API Bearer Token 어노테이션 추가

[33mcommit 602ea477e1b5e3e264d8941edac903d17e21047e[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:28:12 2024 +0900

    [refactor] 댓글 API Bearer Token 어노테이션 추가

[33mcommit aa5b9c36fba81205d84c1532072c9acefcd39e5a[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:27:59 2024 +0900

    [refactor] 일반 게시물 작성 API Bearer Token 어노테이션 추가

[33mcommit e5775a6336cfa4bd2f86835fdbca7cee27edaeca[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Sat Oct 19 00:27:29 2024 +0900

    [refactor] 로그아웃 API @Operation 추가

[33mcommit dab6030eec82ebe34ee4933652837796cd0ed1cc[m
Author: MIN UK JUNG <alsdnrdl001@gmail.com>
Date:   Wed Oct 9 22:57:35 2024 +0900

    [fix] processResource가 copyPrivate 이후에 실행되도록 설정 (#202)

[33mcommit 19b95cd4d7fc9dc39c74fc0dadf48348356dcb7e[m
Author: MIN UK JUNG <alsdnrdl001@gmail.com>
Date:   Wed Oct 9 18:16:52 2024 +0900

    [refactor] build.gradle 수정 (#200)

[33mcommit a3c2ae7489fbf02bb9e53bd2737ece9627f7ac2d[m
Author: MIN UK JUNG <alsdnrdl001@gmail.com>
Date:   Tue Oct 8 15:29:54 2024 +0900

    [fix] 실패하는 테스트 해결 (#198)

[33mcommit 1f258a96e8964e67f7ba76a19f15c801d321649b[m
Merge: 09e4f4a e52b9a8
Author: Eunbi Lee <pask2202@gmail.com>
Date:   Mon Oct 7 16:40:04 2024 +0900

    Merge pull request #195 from f-lab-edu/feat/#193
    
    [#193] 깨진 Test 수정

[33mcommit 09e4f4a24f5e3a7f9ee8146e719979bb9096b889[m
Merge: ee440ff 2ee1749
Author: Eunbi Lee <pask2202@gmail.com>
Date:   Mon Oct 7 16:39:53 2024 +0900

    Merge pull request #194 from f-lab-edu/feat/#189
    
    [#189] 게시물 상세 보기 API Swagger 작성 및 로직 수정

[33mcommit ee440ffbb8c593f9c558307beaf662cbab0c4e07[m
Author: Eunbi Lee <pask2202@gmail.com>
Date:   Fri Oct 4 22:09:14 2024 +0900

    Update Readme.md

[33mcommit 0766d4c32042195eb4dd504560b9a34c7f3acd22[m
Author: MIN UK JUNG <alsdnrdl001@gmail.com>
Date:   Fri Oct 4 17:27:14 2024 +0900

    [#175] Redis 환경 설정 수정 (#196)
    
    * [refactor] Redis Serializer 설정
    
    * [refactor] Redis namespace 분리

[33mcommit e52b9a8e4d882c8bee005901b6535ef9ee021e44[m[33m ([m[1;31morigin/feat/#193[m[33m)[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Fri Oct 4 00:54:27 2024 +0900

    [refactor] tableInit.sql 수정

[33mcommit 3e2ec2f46b23e340346e854f7b642787399fb6a9[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Fri Oct 4 00:54:17 2024 +0900

    [refactor] socialaccount-mapper.xml 깨진 코드 수정

[33mcommit 685c2c9b10930cb4869b5e09eab31b151e61ddf0[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Fri Oct 4 00:54:03 2024 +0900

    [refactor] user-mapper.xml 깨진 코드 수정

[33mcommit 4c4a6c3701aeb1f5519abf4511b1a7c84719dd9d[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Fri Oct 4 00:53:51 2024 +0900

    [refactor] NewsFeedServiceTest 깨진 코드 수정

[33mcommit 4f29e4b62368679d5c4724920adc58b72a49faeb[m
Author: eunbileeme <pask2202@gmail.com>
Date:   Fri Oct 4 00:53:40 2024 +0900

    [refactor] CommentControllerTest 깨진 코드 수정

package com.example.admin.samplerealm.POJO;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by admin on 2018-03-20.
 */

public class AniList extends RealmObject{
    @PrimaryKey
    private int _id; // i int : <애니메이션 고유번호>

    // Adding an index makes queries execute faster on that field.
    @Index
    private String title; // s text : 애니메이션 제목

    private String time; // t char(4) : 시간 2340 -> 23시 40분
    private String genre; //g text : 장르/속성 : 액션 / 메카닉
    private String homeLink; // l(소문자 L) text : 공식사이트 주소 -> http:// 는 생략되어있음. | 입력하지 않은 경우 주소가 "" 빈스트링으로 올 수 있다.
    private boolean bBc; //a boolean : true 진행 | false 금주결방 -> false일 경우 제목앞에 [결방] 을 표시한다. (boolean Broadcast)
    private String sd;
    /** Start Day
     * 애니메이션 시작일 :
     * 20131017 -> 2013년 10월 17일 |
     * 단 날짜형식에 맞지않는 99999999 같은것이 들어갈 수 잇으며 8자리의 숫자형태로 되어있다.
     * 일요일 ~ 토요일 까지의 애니메이션중 현재 날짜보다 미래인 경우 제목앞에 월일을 표시한다. 현재날짜 20120101 값 20131017 - > [10/17
     */
    private String ed;

    public AniList(int _id, String title, String time, String genre, String homeLink, boolean bBc, String sd, String ed) {
        this._id = _id;
        this.title = title;
        this.time = time;
        this.genre = genre;
        this.homeLink = homeLink;
        this.bBc = bBc;
        this.sd = sd.substring(0, 4) + "년 " + sd.substring(4, 6) + "월 " + sd.substring(6, 8) + "일";
        this.ed = ed.substring(0, 4) + "년 " + ed.substring(4, 6) + "월 " + ed.substring(6, 8) + "일";;
    }

    /** End Day
     * 애니메이션 종료일 : sd와 포맷같음
     * 일요일 ~ 토요일 까지의 애니메이션중 현재 날짜보다 과거인 경우 제목앞에 [完]을 표시한다.
     */


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getHomeLink() {
        return homeLink;
    }

    public void setHomeLink(String homeLink) {
        this.homeLink = homeLink;
    }

    public boolean isbBc() {
        return bBc;
    }

    public void setbBc(boolean bBc) {
        this.bBc = bBc;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    public String getGenreTime() {
        return genre + "  -  " + time;
    }

    public String getSdEd() {
        return sd + " ~ " + ed;
    }

}

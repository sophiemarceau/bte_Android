package com.btetop.service;

import com.btetop.bean.BaseBean;
import com.btetop.bean.Course;
import com.btetop.bean.DogsBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;

import java.util.List;

import rx.functions.Action1;

public class DogsService {

    private static List<Course> dogsBean = null;

    public static List<Course> getDogsBean() {
        return dogsBean;
    }

    public static void setDogsBean(List<Course> dogsBean) {
        DogsService.dogsBean = dogsBean;
    }

    //撸庄狗
    private static String lzDog_income = "";
    private static String lzDog_userCount = "";
    private static String lzDog_count = "";
    private static String lzDog_point = "0";


    //波段狗
    private static String bandDog_userCount = "";
    private static String bandDog_point = "0";


    //合约狗
    private static String futureDog_userCount = "";
    private static String futureDog_point = "0";


    private static int futureDogPoing;


    //研究狗
    private static String researchDog_agencyCount = "";
    private static String researchDog_count = "";
    private static String researchDog_point = "0";

    //盯盘狗

    private static String stareDog_userCount = "";
    private static String stareDog_point = "0";

    public static String getLzDog_income() {
        return lzDog_income;
    }

    public static int getFutureDogPoing() {
        return futureDogPoing;
    }

    public static void setFutureDogPoing(int futureDogPoing) {
        DogsService.futureDogPoing = futureDogPoing;
    }

    public static void setLzDog_income(String lzDog_income) {
        DogsService.lzDog_income = lzDog_income;
    }

    public static String getLzDog_userCount() {
        return lzDog_userCount;
    }

    public static void setLzDog_userCount(String lzDog_userCount) {
        DogsService.lzDog_userCount = lzDog_userCount;
    }

    public static String getLzDog_count() {
        return lzDog_count;
    }

    public static void setLzDog_count(String lzDog_count) {
        DogsService.lzDog_count = lzDog_count;
    }

    public static String getLzDog_point() {
        return lzDog_point;
    }

    public static void setLzDog_point(String lzDog_point) {
        DogsService.lzDog_point = lzDog_point;
    }

    public static String getBandDog_userCount() {
        return bandDog_userCount;
    }

    public static void setBandDog_userCount(String bandDog_userCount) {
        DogsService.bandDog_userCount = bandDog_userCount;
    }

    public static String getBandDog_point() {
        return bandDog_point;
    }

    public static void setBandDog_point(String bandDog_point) {
        DogsService.bandDog_point = bandDog_point;
    }

    public static String getFutureDog_userCount() {
        return futureDog_userCount;
    }

    public static void setFutureDog_userCount(String futureDog_userCount) {
        DogsService.futureDog_userCount = futureDog_userCount;
    }

    public static String getFutureDog_point() {
        return futureDog_point;
    }

    public static void setFutureDog_point(String futureDog_point) {
        DogsService.futureDog_point = futureDog_point;
    }

    public static String getStareDog_userCount() {
        return stareDog_userCount;
    }

    public static void setStareDog_userCount(String stareDog_userCount) {
        DogsService.stareDog_userCount = stareDog_userCount;
    }

    public static String getStareDog_point() {
        return stareDog_point;
    }

    public static void setStareDog_point(String stareDog_point) {
        DogsService.stareDog_point = stareDog_point;
    }

    public static String getResearchDog_agencyCount() {
        return researchDog_agencyCount;
    }

    public static void setResearchDog_agencyCount(String researchDog_agencyCount) {
        DogsService.researchDog_agencyCount = researchDog_agencyCount;
    }

    public static String getResearchDog_count() {
        return researchDog_count;
    }

    public static void setResearchDog_count(String researchDog_count) {
        DogsService.researchDog_count = researchDog_count;
    }

    public static String getResearchDog_point() {
        return researchDog_point;
    }

    public static void setResearchDog_point(String researchDog_point) {
        DogsService.researchDog_point = researchDog_point;
    }

    public static void loadSetting(Action1 callback) {
        try {

            BteTopService.getDogsInfo()
                    .compose(RxUtil.<BaseBean<DogsBean>>mainAsync())
                    .subscribe(new Action1<BaseBean<DogsBean>>() {
                        @Override
                        public void call(BaseBean<DogsBean> baseBean) {
                            if (baseBean != null) {
                                DogsBean data = baseBean.getData();
                                List<Course> dogsList = data.getDogsList();

                                //添加一个其他未加入狗的功能
//                                Course course = new Course();
//                                course.setType("OTHER");
//                                dogsList.add(course);
                                setDogsBean(dogsList);
                                setLzDog_point(data.getLzDog().getPoint() + "");
                                setBandDog_point(data.getBandDog().getPoint() + "");
                                setFutureDog_point(data.getFutureDog().getPoint() + "");
                                setFutureDogPoing(data.getFutureDog().getPoint());
                                setFutureDog_userCount(data.getFutureDog().getUserCount() + "");
                                setStareDog_point(data.getStareDog().getPoint() + "");
                                setStareDog_userCount(data.getStareDog().getUserCount() + "");
                                callback.call(null);
                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });

        } catch (Exception e) {
        }
    }
}

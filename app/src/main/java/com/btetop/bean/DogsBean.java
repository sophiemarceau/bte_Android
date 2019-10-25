package com.btetop.bean;

import com.btetop.config.Constant;

import java.util.ArrayList;
import java.util.List;

public class DogsBean {


    /**
     * bandDog : {"userCount":3653,"point":25,"notice":141}
     * lzDog : {"income":5.48,"userCount":4220,"count":48,"point":15,"notice":97}
     * futureDog : {"userCount":3742,"point":35,"notice":1}
     * stareDog : {"userCount":1016,"point":35,"notice":0}
     * researchDog : {"agencyCount":4,"count":42,"notice":42}
     */

    private Course bandDog;
    private Course lzDog;
    private Course futureDog;
    private Course stareDog;
    private Course researchDog;
    private Course lianchacha;

    public Course getBandDog() {
        return bandDog;
    }

    public void setBandDog(Course bandDog) {
        this.bandDog = bandDog;
    }

    public Course getLzDog() {
        return lzDog;
    }

    public void setLzDog(Course lzDog) {
        this.lzDog = lzDog;
    }

    public Course getFutureDog() {
        return futureDog;
    }

    public void setFutureDog(Course futureDog) {
        this.futureDog = futureDog;
    }

    public Course getStareDog() {
        return stareDog;
    }

    public void setStareDog(Course stareDog) {
        this.stareDog = stareDog;
    }

    public Course getResearchDog() {
        return researchDog;
    }

    public void setResearchDog(Course researchDog) {
        this.researchDog = researchDog;
    }

    public Course getLianchacha() {
        return lianchacha;
    }

    public void setLianchacha(Course lianchacha) {
        this.lianchacha = lianchacha;
    }

    public List<Course> getDogsList() {

        List<Course> dogsList = new ArrayList<>();
        lzDog.setType(Constant.EVENT_LZ_DOG);
        bandDog.setType(Constant.EVENT_BAND_DOG);
        futureDog.setType(Constant.EVENT_FUTURE_DOG);
        stareDog.setType(Constant.EVENT_STRATEGY_DOG);
        researchDog.setType(Constant.EVENT_RESEARCH_DOG);
        lianchacha.setType(Constant.EVENT_LIAN_CHACHA);
        dogsList.add(futureDog);
        dogsList.add(lzDog);
        dogsList.add(bandDog);
        dogsList.add(stareDog);
        dogsList.add(researchDog);
        dogsList.add(lianchacha);
        return dogsList;
    }


}

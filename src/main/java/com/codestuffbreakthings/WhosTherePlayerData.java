package com.codestuffbreakthings;

import lombok.Getter;

public class WhosTherePlayerData {
    @Getter
    private final String username;
    //@Getter
    //private final Integer distance;
    //public WhosTherePlayerData(String username, Integer distance) {
    public WhosTherePlayerData(String username){
        this.username = username;
    //    this.distance = distance;
    }

    @Override
    public String toString() {
        //return "Username: " + username + ", Distance: " + distance;
        return "Username: " + username;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WhosTherePlayerData whosTherePlayerData = (WhosTherePlayerData) obj;
        return username.equals(whosTherePlayerData.username);
    }
}

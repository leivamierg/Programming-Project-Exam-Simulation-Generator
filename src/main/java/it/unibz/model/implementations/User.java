package it.unibz.model.implementations;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private List<Badge> badges;
    private int numberOfBadges;
    private int streak;
    private String challengeDate;

    public User()
    {
        this.username = "";
        this.badges = new ArrayList<>();
        this.streak = 0;
        this.challengeDate = "";
        this.numberOfBadges = 0;
    }

    public User(String username)
    {
        this.username = username;
        this.badges = new ArrayList<>();
        this.streak = 0;
        this.challengeDate = "";
        this.numberOfBadges = 0;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }


    public void setBadges(List<Badge> badges)
    {
        this.badges = badges;
        updateNumberOfBadges();
    }

    public String getChallengeDate()
    {
        return challengeDate;
    }

    public void setChallengeDate(String challengeDate)
    {
        this.challengeDate = challengeDate;
    }

    // Other methods
    public void addBadge(Badge badge)
    {
        this.badges.add(badge);
    }

    public void incrementStreak()
    {
        this.streak += 1;
    }

    public void resetStreak()
    {
        this.streak = 0;
    }

    private void updateNumberOfBadges() {
        this.numberOfBadges = badges.size();
    }

    @Override
    public String toString() {
        StringBuilder badgesString = new StringBuilder();
        for (Badge badge : badges) {
            badgesString.append(badge.toString()).append("\n");
        }

        return "User Information:\n" +
                "Username: " + username + "\n" +
                "Badges: " + numberOfBadges + "\n" + badgesString +
                "Streak: " + streak + "\n" +
                "Last challenge Date: " + challengeDate + "\n";
    }
}

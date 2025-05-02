package com.helwan.survivalgame.story;

import java.util.ArrayList;
import java.util.List;

public class StoryManager {
    private List<Quest> quests;
    private int currentQuestIndex;

    public StoryManager() {
        quests = new ArrayList<>();
        currentQuestIndex = 0;
        loadQuests();
    }

    private void loadQuests() {
        quests.add(new Quest("Find Shelter", "Find a safe place to survive the night."));
        quests.add(new Quest("Gather Food", "Collect enough food to sustain yourself."));
        quests.add(new Quest("Defeat the Enemy", "Eliminate the threat to your survival."));
    }

    public Quest getCurrentQuest() {
        if (currentQuestIndex < quests.size()) {
            return quests.get(currentQuestIndex);
        }
        return null;
    }

    public void completeCurrentQuest() {
        if (currentQuestIndex < quests.size()) {
            quests.get(currentQuestIndex).complete();
            currentQuestIndex++;
        }
    }

    public boolean isStoryComplete() {
        return currentQuestIndex >= quests.size();
    }
}

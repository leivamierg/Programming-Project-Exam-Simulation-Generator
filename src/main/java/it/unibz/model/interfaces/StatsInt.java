package it.unibz.model.interfaces;

import it.unibz.model.implementations.Score;
import it.unibz.model.implementations.Simulation;
import it.unibz.model.implementations.Subtopic;
import it.unibz.model.implementations.Topic;

import java.util.List;
import java.util.Map;

public interface StatsInt {
    /**
     * update the stats at the end of every sim
     *
     * @param simulation the simulation that has just terminated
     */
    void updateStats(Simulation simulation);

    /**
     * @param topic the topic you want to compare the stats on
     * @param start the sim id you want to start comparing the stats (from 1 to n)
     * @param end   the sim id you want to end comparing the stats (from 1 to n)
     * @return a string containing the comparison of the stats: to the left the
     *         starting stats, to the right the ending ones
     */
    String compareStats(Topic topic, int start, int end);

    /**
     *
     * @param topic the topic you want to compare the stats on
     * @param start the sim id you want to start comparing the stats (from 1 to n)
     * @return a string containing the comparison of the stats: to the left the
     *         starting stats, to the right the current ones
     */
    String compareStats(Topic topic, int start);

    /**
     *
     * @param subtopic the subtopic you want to compare the stats on
     * @param start    the sim id you want to start comparing the stats (from 1 to
     *                 n)
     * @param end      the sim id you want to end comparing the stats (from 1 to n)
     * @return a string containing the comparison of the stats: to the left the
     *         starting stats, to the right the ending ones
     */
    String compareStats(Subtopic subtopic, int start, int end);

    /**
     *
     * @param subtopic the subtopic you want to compare the stats on
     * @param start    the sim id you want to start comparing the stats (from 1 to
     *                 n)
     * @return a string containing the comparison of the stats: to the left the
     *         starting stats, to the right the current ones
     */
    String compareStats(Subtopic subtopic, int start);

    /**
     *
     * @return a string containing all the stats
     */
    String showStats();

    /**
     *
     * @return a map containing the evolution of the stats of every topic after
     *         every sim
     */

    Map<String, List<Score>> getTopicToStats();

    /**
     *
     * @return a map containing the evolution of the stats of every subtopic after
     *         every sim
     */
    Map<String, List<Score>> getSubtopicToStats();

    /**
     *
     * @return a the general stats after every sim
     */

    Score getGeneralStats();
}

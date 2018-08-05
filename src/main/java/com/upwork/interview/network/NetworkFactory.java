package com.upwork.interview.network;

import com.upwork.interview.network.impl.observerpattern.NetworkObserverPattern;
import com.upwork.interview.network.impl.search.NetworkSearch;

public class NetworkFactory {

    public static Network getNetwork(int networkSize, NetworkSolutionType solution) {
        if (solution == NetworkSolutionType.OBSERVER) {
            return new NetworkObserverPattern(networkSize);
        }
        return new NetworkSearch(networkSize);
    }

}

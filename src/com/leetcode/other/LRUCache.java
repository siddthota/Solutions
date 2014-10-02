package com.leetcode.other;

import java.util.HashMap;
import java.util.Map;

/**
 *  Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and set.
 *
 *  get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 *  set(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
 *
 *  Created by Xiaomeng on 8/19/2014.
 */
public class LRUCache {
    Map<Integer, DoublyLinkedListNode> cache;
    DoublyLinkedListNode head;
    DoublyLinkedListNode end;
    int capacity;
    int len;

    public LRUCache(int capacity) {
        cache = new HashMap<Integer, DoublyLinkedListNode>();
        this.capacity = capacity;
        this.len = 0;
    }

    public int get(int key) {
        if(cache.containsKey(key)){
            DoublyLinkedListNode node = cache.get(key);
            removeNode(node);
            setHead(node);
            return node.value;
        }else{
            return -1;
        }
    }

    public void set(int key, int value) {
        if(cache.containsKey(key)){
            DoublyLinkedListNode node = cache.get(key);
            node.value = value;
            removeNode(node);
            setHead(node);
        }else{
            DoublyLinkedListNode node = new DoublyLinkedListNode(key, value);
            if(len == capacity){
                cache.remove(end.key);
                removeNode(end);
                cache.put(key, node);
                setHead(node);
            }else{
                cache.put(key, node);
                setHead(node);
                len++;
            }
        }
    }

    private  void setHead(DoublyLinkedListNode node){
        node.prev = null;
        node.next = head;
        if(head != null) head.prev = node;
        head = node;
        end = end == null ? node : end;
    }

    private void removeNode(DoublyLinkedListNode node){
        DoublyLinkedListNode prev = node.prev;
        DoublyLinkedListNode next = node.next;
        if(prev == null){
            head = next;
        }else{
            prev.next = next;
        }

        if(next == null){
            end = prev;
        }else{
            next.prev = prev;
        }
    }

    private class DoublyLinkedListNode{
        int key;
        int value;
        DoublyLinkedListNode prev;
        DoublyLinkedListNode next;

        public DoublyLinkedListNode(int key, int value){
            this.key = key;
            this.value = value;
        }
    }
}
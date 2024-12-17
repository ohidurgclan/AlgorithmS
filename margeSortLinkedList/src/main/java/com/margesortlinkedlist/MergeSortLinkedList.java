package com.margesortlinkedlist;

import java.util.Scanner;
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}
public class MergeSortLinkedList {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Input How many elements wants in the linked list:...");
        int n = input.nextInt();

        System.out.println("Input The elements in the linked list:...");
        Node head = null;
        Node tail = null;

        for (int i = 0; i < n; i++) {
            int data = input.nextInt();
            Node newNode = new Node(data);
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }
        System.out.println("Linked list before sorting:");
        printLinkedList(head);

        head = mergeSort(head);

        System.out.println("Linked list after sorting:");
        printLinkedList(head);

        input.close();
    }
    public static Node mergeSort(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node middle = findMiddle(head);
        Node nextToMiddle = middle.next;
        middle.next = null;

        Node left = mergeSort(head);
        Node right = mergeSort(nextToMiddle);

        return merge(left, right);
    }

    public static Node findMiddle(Node head) {
        if (head == null) {
            return null;
        }

        Node slow = head;
        Node fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public static Node merge(Node left, Node right) {
        Node result = null;

        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        if (left.data <= right.data) {
            result = left;
            result.next = merge(left.next, right);
        } else {
            result = right;
            result.next = merge(left, right.next);
        }

        return result;
    }

    public static void printLinkedList(Node head) {
        while (head != null) {
            System.out.print(head.data + " , ");
            head = head.next;
        }
        System.out.println();
        System.out.println();
    }
}
/*******
Original Question : https://stackoverflow.com/questions/66082680/

Swap two elements from a linked list given a position:

I want to program a function that swaps the element of a list given a position, for example:

original list:
10-->20-->30-->40-->50-->60
and if I give position 3 I would like the resulting list to be:

10-->20-->40-->30-->50-->60
Consider that I do not want to swap the elements, but to swap the nodes. 
Also, I have implemented a version, but using an auxiliary linked list, which it works, 
but I am curious about how to solve this only using the links of the list. So far I have the following:


***/

package LinkedList;

class Node {
    public int elem;
    public Node next;
    public Node(int elem){
        this.elem=elem;
    }
}

class LinkedList {
    Node first;
    Node front;
    int c;

    public void add(int n) {
        Node node = new Node(n);
        if (front == null) {
            first = node;
        } else {
            front.next = node;
        }
        front = node;
        c++;
    }

    public void print() {
        Node t = first;
        while (t != null) {
            System.out.println(t.elem);
            t = t.next;
        }
    }

    public void swap(int pos) {
        Node prev = null;
        Node curr = first;
        for(int c = 1; curr != null && c < pos; c++){
                prev = curr;
                curr = curr.next;
        }
        if (pos <= 0 || curr == null || curr.next == null) {
            System.out.println("Next element is null or pos is invalid");
        }
        else if(prev == null){
            Node t = curr.next;
            curr.next = curr.next.next;
            t.next = first;
            first = t;
        }
        else {
            Node t = curr.next;
            curr.next = curr.next.next;
            t.next = prev.next;
            prev.next = t;
        }
    }

    public static void main(String[] args) {
        LinkedList l = new LinkedList();

        l.add(10);
        l.add(20);
        l.add(30);
        l.add(40);
        l.add(50);
        l.add(60);
        l.swap(1);
        l.print();
    }
}

// Written by Giacomo Siniscalchi, sinis006
public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node<T> first;
    private int size = 0;
    boolean isSorted = true;

    public LinkedList() {
        first = null;
    }

    public boolean add(T element) {
        if (element == null) return false;

        if (first == null) {
            first = new Node<T>(element);
            isSorted = true;
        } else {
            Node<T> cursor = first;
            //Search for the end of the list
            while(cursor.getNext() != null) {
                cursor = cursor.getNext();
            }
            //Add element to the end of the list
            cursor.setNext(new Node<T>(element));
            if(isSorted && cursor.compareTo(cursor.getNext()) > 0) isSorted = false;
            //System.out.println(isSorted + ", " + cursor + ", " + cursor.getNext() + ", " + cursor.getNext().getNext());

        }
        //After done adding, return true
        size++;
        return true;
    }

    public boolean add(int index, T element) {
        if (element == null) return false;
        if (isEmpty() && index == 0) {
            first = new Node<T> (element); //if the list is empty, but we want to add at index=0, it's good.
            size++;
            isSorted = true;
            return true;
        }
        else if (index == 0) { //index is 0 but list isn't empty, have to change head
            first = new Node<T>(element, first);
            size++;
            isSorted = isSorted && (first.compareTo(first.getNext()) <= 0);
            return true;
        }
        else if ((isEmpty() && index > 0) || index < 0) return false; //if the list is empty, but the index is > 0, or if the index is < 0, it's a bad input.
        Node<T> cursor = first;
        int i = 0;
        while (true) {
            //if we reach the index, we can add.
            if (i + 1 == index) {
                cursor.setNext(new Node<T>(element, cursor.getNext()));
                isSorted = isSorted && cursor.compareTo(cursor.getNext()) <= 0;
                if(isSorted && cursor.getNext().getNext() != null ) isSorted = isSorted && cursor.getNext().compareTo(cursor.getNext().getNext()) <= 0;
                //System.out.println(isSorted + ", " + cursor + ", " + cursor.getNext() + ", " + cursor.getNext().getNext());
                size++;
                return true;
            }
            //if we reach the end of the list and haven't gotten to the target index, we can't add.
            if (i + 1 <= index && cursor.getNext() == null) return false;
            else {
                i++;
                if(cursor == null) return false;
                cursor = cursor.getNext();
            }
        }
    }

    public void clear() {
        first = null;
        size = 0;
        isSorted = true;
    }

    public T get(int index) {
        if(first == null || index < 0) return null;
        Node<T> cursor = first;
        int i = 0;
        while(i < index) {
            if (cursor.getNext() != null) {
                i++;
                cursor = cursor.getNext();
            } else return null;
        }
        return cursor.getData();
    }

    public int indexOf(T element) {
        Node<T> cursor = first;
        int i = 0;
        if(isSorted) {
            while(cursor != null) {
                int compare = cursor.getData().compareTo(element);
                if (compare == 0) return i;
                if(compare > 0) return -1;
                i++;
                cursor = cursor.getNext();
            }
        }
        while(cursor != null) {
            if (cursor.getData().compareTo(element) == 0) return i;
            i++;
            cursor = cursor.getNext();
        }
        return -1;
    }

    public boolean isEmpty() { return size==0; }

    public int size() {
        return size;
        /*Node cursor = first;
        int size = 0;
        while(cursor != null) {
            size++;
            cursor = cursor.getNext();
        }
        return size;*/
    }

    /*public void sort() {
        Node<T> newFirst = new Node<T>(first.getData(), null);
        Node<T> last = newFirst;
        Node<T> cursor = first;
        while(cursor.getNext() != null) {
            Node<T> next = cursor.getNext();
            if(next.compareTo(cursor) < 0) {
                while()
            }
        }
    }*/

    public void sort() {
        if (isSorted) return;
        isSorted = true;
        Node<T> cursor = first;
        while(cursor.getNext() != null) {
            T nextData = cursor.getNext().getData();
            T cursorData = (T) cursor.getData();
            //Check if the next element is less than the previous
            //If the next element is less than the previous, save the next node, then set the current node's next to the next, next node
            if(cursor.getNext().compareTo(cursor) < 0) {
                //System.out.println(nextData + " < " + cursorData);
                Node<T> next = cursor.getNext();
                cursor.setNext(next.getNext());
                //Make a new cursor, go to the beginning of the list
                Node<T> cursor2 = first;
                //Keep going through the list until we find where the new node should be placed
                if(nextData.compareTo(cursor2.getData()) < 0) {
                    next.setNext(first);
                    first = next;
                } else {
                    //If there are nodes with the same data, keep going through the list until new data is found (to ensure stability)
                    while (((cursor2.getNext().getData())).compareTo(nextData) <= 0) {
                        cursor2 = cursor2.getNext();
                    }
                    next.setNext(cursor2.getNext());
                    cursor2.setNext(next);
                }
            }//if
            else cursor = cursor.getNext();
        }//while
    }//sort()



    //EXTRA STUFF: if it is sorted before removing, list will still be sorted
    // if not sorted before removing, check if sorted, as it may be sorted now.
    public T remove(int index) {
        if (size==0) return null; //if the list is empty return null
        Node<T> cursor = first;
        if(index == 0) {
            T data = first.getData();
            first = first.getNext();
            size--;
            if(!isSorted) checkSorted();
            return data;
        }
        int i = 0;
        while (cursor.getNext() != null) {
            //if we reach the end of the list and haven't gotten to the target index, we can't remove
            //if (i + 1 < index && cursor.getNext() == null) return null;
            //if we reach the index, we can add.
            if (i + 1 == index) {
                Node<T> toRemove = cursor.getNext();
                cursor.setNext(toRemove.getNext());
                size--;
                if(!isSorted) checkSorted();
                return toRemove.getData();
            }
            else {
                i++;
                cursor = cursor.getNext();
            }
        }
        return null;
    }

    public void equalTo(T element) {

        //handle when head is not equal to element
        while(first != null && first.getData().compareTo(element) != 0) {
            first = first.getNext();
            size--;
        }
        if(isSorted) {
            Node<T> cursor = first;
            while(cursor != null && cursor.getNext()!= null && cursor.getNext().getData().compareTo(element) != 0) {
                cursor = cursor.getNext();
            }
            cursor.setNext(null);
            return;
        }
        Node<T> cursor = first;
        while(cursor != null && cursor.getNext() != null) {
            if(cursor.getNext().getData().compareTo(element)!=0) {
                cursor.setNext(cursor.getNext().getNext());
                size--;
            } else cursor = cursor.getNext();
        }
        isSorted = true;
    }
    //check if sorted after reversing
    public void reverse() {
        /*
        Goes through the list with 2 pointers
        with list:
                        1  2  3  4  5  6 //where first node points to second, second points to third...etc
                       [1][2] 3  4  5  6 //make the second node point to the first node
                        1 [2][3] 4  5  6 //make the third node point to the second node
                        1  2 [3][4] 5  6 //make the fourth node point to the third note.... etc
        */
        Node<T> firstCursor = first;
        Node<T> secondCursor = firstCursor.getNext();
        firstCursor.setNext(null);
        while(secondCursor != null) {
            Node<T> tempNext = secondCursor.getNext();
            secondCursor.setNext(firstCursor);
            firstCursor = secondCursor;
            secondCursor = tempNext;
        }
        first = firstCursor;
        checkSorted();
    }

    public void intersectBetter(List<T> otherList) {
        if(otherList.isEmpty()) return;
        LinkedList<T> other = (LinkedList<T>) otherList;
        sort();
        other.sort();
        Node<T> thisCursor = first;
        Node<T> otherCursor = other.first;
        //check if first of list is in the other list


        while(thisCursor != null && otherCursor != null) {
            if(thisCursor.compareTo(otherCursor) == 0) { //found match
                Node<T> nextCursor = first.getNext();
                while (nextCursor != null && nextCursor.compareTo(thisCursor) == 0) {
                    nextCursor = nextCursor.getNext();
                }
                thisCursor.setNext(nextCursor);
            }

        }
    }

    public void intersect(List<T> otherList) { //fix, can't have duplicates
        if (otherList.isEmpty()) return;
        LinkedList<T> other = (LinkedList<T>) otherList;
        sort();
        other.sort();
        Node<T> thisCursor = first;
        Node<T> newHead = null;
        Node<T> newCursor = null;
        while(thisCursor != null) {
            if (otherList.indexOf(thisCursor.getData()) != -1) {
                if(newHead == null) {
                    newHead = new Node<T>(thisCursor.getData());
                    newCursor = newHead;
                } else {
                    newCursor.setNext(new Node<T>(thisCursor.getData()));
                    newCursor = newCursor.getNext();
                }
            }
            do {
                    thisCursor = thisCursor.getNext();
            } while(thisCursor != null && (thisCursor.getNext() != null && thisCursor.getNext().compareTo(thisCursor) == 0));
        }
        first = newHead;
    }
    public T getMin() {
        if (size==0) return null;
        if(isSorted) return first.getData();
        T min = first.getData();
        Node<T> cursor = first;
        //traverses to list
        while(cursor.getNext() != null) {
            cursor = cursor.getNext();
            T compare = (T)cursor.getData();
            //if the current node has a lower value than the saved min, replace the min value
            if (min.compareTo(compare) > 0) min = compare;
        }
        return min;
    }

    public T getMax() {
        if (size==0) return null;
        if(isSorted) return get(size - 1);
        T max = (T)first.getData();
        Node<T> cursor = first;
        //traverse the list
        while(cursor.getNext() != null) {
            cursor = cursor.getNext();
            T compare = (T)cursor.getData();
            //if the current node has a value greater than the current saved max value, replace the max value
            if (max.compareTo(compare) < 0) max = compare;
        }
        return max;
    }

    public String toString() {
        Node<T> cursor = first;
        String r = "";
        int i = 0;
        while(cursor != null) {
            r +=  "\n" + cursor.getData().toString();
            cursor = cursor.getNext();
            i++;
        }
        return r;
    }

    public boolean isSorted() {
        return isSorted;
    }

    public boolean checkSorted() {
        Node<T> cursor = first;
        if (cursor == null) {
            isSorted = true;
            return true;
        }
        while(cursor.getNext() != null) {
            if (cursor.compareTo(cursor.getNext()) > 0) {
                isSorted = false;
                return false;
            }
            cursor = cursor.getNext();
        }
        isSorted = true;
        return true;
    }

}
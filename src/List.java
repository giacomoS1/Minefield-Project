/**
 * Updated by Noah Park and Michael Markiewicz on 03.03.2021.
 *
 * @param <T> Generic type for the list interface.
 */
public interface List<T extends Comparable<T>> {

  /**
   * Add an element to end of the list. If element is null,
   * it will NOT add it and return false.  Otherwise, it
   * will add it and return true. Updates isSorted to false if
   * the element added breaks sorted order.
   *
   * @param element element to be added to the list.
   * @return if the addition was successful.
   */
  boolean add(T element);

  /**
   * Add an element at specific index. This method should
   * also shift the element currently at that position (if
   * any) and any subsequent elements to the right (adds
   * one to their indices). If element is null, or if index
   * index is out-of-bounds (index < 0 or index >= size_of_list),
   * it will NOT add it and return false. Otherwise it will
   * add it and return true. See size() for the definition
   * of size of list. Also updates isSorted variable to false if the
   * element added breaks the current sorted order.
   *
   * @param index index at which to add the element.
   * @param element element to be added to the list.
   * @return if the addition was successful.
   */
  boolean add(int index, T element);

  /**
   * Remove all elements from the list and updates isSorted accordingly.
   */
  void clear();

  /**
   * Return the element at given index. If index is
   * out-of-bounds, it will return null.
   *
   * @param index index to obtain from the list.
   * @return the element at the given index.
   */
  T get(int index);

  /**
   * Return the first index of element in the list. If element
   * is null or not found in the list, return -1. If isSorted is
   * true, uses the ordering of the list to increase the efficiency
   * of the search.
   *
   *
   * @param element element to be found in the list.
   * @return first index of the element in the list.
   */
  int indexOf(T element);

  /**
   * Return true if the list is empty and false otherwise.
   *
   * @return if the list is empty.
   */
  boolean isEmpty();

  /**
   * size() return the number of elements in the list. Be careful
   * not to confuse this for the length of a list like for an ArrayList.
   * For example, if 4 elements are added to a list, size will return
   * 4, while the last index in the list will be 3. Another example
   * is that an ArrayList like [5, 2, 3, null, null] would have a size
   * of 3 for an ArrayList.
   * ArrayList and LinkedList hint: create a class variable in both ArrayList
   * and LinkedList to keep track of the sizes of the respective lists.
   *
   * @return size of the list.
   */
  int size();

  /**
   * Sort the elements of the list in ascending order using insertion sort.
   * If isSorted is true, do NOT re-sort.
   * Hint: Since T extends Comparable, you will find it useful
   * to use the public int compareTo(T other) method.
   * Updates isSorted accordingly.
   */
  void sort();

  /**
   * Remove whatever is at index 'index' in the list and return
   * it. If index is out-of-bounds, return null. For the ArrayList,
   * elements to the right of index should be shifted over to maintain
   * contiguous storage. Must check to see if the list is sorted after removal
   * of the element at the given index and updates isSorted accordingly.
   *
   * @param index position to remove from the list.
   * @return the removed element.
   */
  T remove(int index);

  /**
   * Removes all elements of the list that are not equal to 'element'. If element is null, don't do anything.
   * When this function returns, the only elements that should be left in this list
   * are equal to 'element'. This method should not change the ordering of the list.
   * If the list is sorted, use this fact to increase the efficiency of this method.
   * This method should be done IN PLACE. Do not use any extra data structures to
   * solve this problem. (You are NOT allowed to create a new array for this function).
   * Updates isSorted accordingly.
   *
   * @param element type of element to be kept in the list.
   */
  void equalTo(T element);

  /**
   * Reverses the list IN PLACE. Any use of intermediate data structures will yield
   * your solution invalid.
   */
  void reverse();

  /**
   * Creates the intersection of 2 lists. The resulting intersection should be reflected in the calling List
   * If otherList is null, do not attempt to merge.
   *
   * Sort should be called on the List that calls "intersect()", since the resulting list should be sorted.
   * Moreover, should also update isSorted to true.
   * Note, you will have to cast otherList from a List<T> type to a ArrayList<T> type or LinkedList<T>.
   *
   * After checking for possible errors, you're first two lines of code should be:
   * LinkedList<T> other = (LinkedList<T>) otherList; or ArrayList<T> other = (Arraylist<T>) otherList;
   * sort();
   *
   * @param otherList list to be used for finding the intersection.
   */
  void intersect(List<T> otherList);

  /**
   * Returns the minimum value of the List
   * Note, consider how sorting can effect or optimize this solution
   */
  T getMin();

  /**
   * Returns the Maximum value of the List
   * Note, consider how sorting can effect or optimize this solution
   */
  T getMax();

  /**
   * Note that this method exists for debugging purposes.
   * It calls the toString method of the underlying elements in
   * the list in order to create a String representation of the list.
   * The format of the toString should appear as follows:
   * Element_1
   * Element_2
   * .
   * .
   * .
   * Element_n
   * Watch out for extra spaces or carriage returns. Each element
   * should be printed on its own line.
   *
   * @return String representation of the list.
   */
  String toString();

  /**
   * Simply returns the isSorted attribute.
   *
   * @return isSorted boolean attribute.
   */
  boolean isSorted();
}
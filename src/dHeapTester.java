import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class dHeapTester {

    dHeap<Integer> heap;
    @BeforeEach
    void setUp() {
        heap = new dHeap<>();
    }
    @Test
    void tests() {//All methods tested together here
        //First heap, binary max heap
        for(int i : new int[]{42, 19, 33, 47, 89, 20, 77, 55, 90, 85, 100, 7})
            heap.add(i);
        assertEquals(heap.size(), 12);
        assertEquals(heap.remove(), 100);
        assertEquals(heap.element(), 90);
        heap.clear();
        assertThrows(NoSuchElementException.class, () -> {
            heap.remove();
        });
        assertThrows(NullPointerException.class, () -> {
            heap.add(null);
        });
        assertThrows(NoSuchElementException.class, () -> {
            heap.element();
        });

        //Second heap, 4-ary min heap
        heap = new dHeap<>(4, 6, false);
        for(int i : new int[]{24, 50, 31, 53, 56, 38})
            heap.add(i);
        assertEquals(heap.size(), 6);
        assertEquals(heap.remove(), 24);
        assertEquals(heap.element(), 31);
        heap.add(10);
        assertEquals(heap.remove(), 10);
        heap.add(45);
        assertEquals(heap.remove(), 31);
        heap.add(38);
        assertEquals(heap.remove(), 38);
        assertEquals(heap.element(), 38);
        assertEquals(heap.size(), 5);
        heap.clear();
        assertEquals(heap.size(), 0);
        heap.clear();
        assertEquals(heap.size(), 0);
    }
    @Test
    void docTests(){
        heap = new dHeap<>(1, 3, true);
        for(int i = 0; i < 30; i++)
            heap.add(i);

    }
}
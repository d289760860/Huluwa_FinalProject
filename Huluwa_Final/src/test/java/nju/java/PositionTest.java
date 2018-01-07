package nju.java;

import org.junit.Test;

/** 
* Position Tester. 
* 
* @author <Authors name> 
* @since <pre>һ�� 7, 2018</pre> 
* @version 1.0 
*/ 
public class PositionTest { 

@Test
public void testEqualsP() throws Exception {
    Position p1 = new Position(1,1);
    Position p2 = new Position(2,1);
    assert (p1.equals(p1));
    assert (p2.equals(p2));
    assert (!p1.equals(p2));
    assert (!p2.equals(p1));

//TODO: Test goes here... 
}
@Test
public void testEqualsForXY() throws Exception { 
    Position p = new Position(1,2);
    assert (p.equals(1,2));
    assert (!p.equals(1,3));

}
@Test
public void testValid() throws Exception {
    Position p1 = new Position(-1,0);
    Position p2 = new Position(0,-1);
    Position p3 = new Position(0,Constants.NUMCELLY);
    Position p4 = new Position(Constants.NUMCELLX,0);
    assert(!p1.valid());
    assert(!p2.valid());
    assert(!p3.valid());
    assert(!p4.valid());
//TODO: Test goes here... 
}

} 

package com.yazuo.erp.base

import javax.annotation.Resource


import spock.lang.*  

class SpockG extends Specification {
	
  def setupSpec() {
  }  
  def "first integration test"() {
   
  	when: 1==1
	then: print true
  }   
  
  def "comparing x and y"() { 
	  def x = 1
	  def y = 2
    
	  expect:
	  x < y    // OK
	  x == y   // BOOM!
	}
} 


//class MyJUnitDaoBaseWithTrans {
//	@Delegate JUnitDaoBaseWithTrans trans
//}
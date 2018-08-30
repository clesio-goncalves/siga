package siga.capau.controller;

import java.util.ArrayList;
import java.util.List;

public class teste {
	
	public static void main(String[] args) {
		List<Long> numero = new ArrayList<>();
		numero.add((long) 11);
		numero.add((long) 111);
		numero.add((long) 1111);
		
		if (numero.contains((long) 1)) {
			System.out.println("contem");
		} else {
			System.out.println("Não contem");
		}
		
	}

}

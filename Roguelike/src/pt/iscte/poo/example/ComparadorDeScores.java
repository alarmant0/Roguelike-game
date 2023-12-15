package pt.iscte.poo.example;

import java.util.Comparator;

public class ComparadorDeScores implements Comparator<Score> {

	@Override
	public int compare(Score s1, Score s2) {
		if (s1 == s2) {
			Comparator<String> comp = String.CASE_INSENSITIVE_ORDER;
			return comp.compare(s1.getNome(), s2.getNome());
		}
		return s2.getScore() - s1.getScore();
	}

}

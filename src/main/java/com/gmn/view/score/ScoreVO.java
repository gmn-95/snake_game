package com.gmn.view.score;

import java.time.LocalDateTime;
import java.util.Objects;

public class ScoreVO {

    private int score;
    private LocalDateTime data;

    public ScoreVO(int score, LocalDateTime data) {
        this.score = score;
        this.data = data;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ScoreVO{" +
                "score=" + score +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreVO scoreVO = (ScoreVO) o;
        return score == scoreVO.score && Objects.equals(data, scoreVO.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, data);
    }
}

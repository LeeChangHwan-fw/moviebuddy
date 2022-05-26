package moviebuddy.domain;

import moviebuddy.ApplicationException;
import moviebuddy.util.FileSystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MovieFinder {

    private final MovieReader movieReader;
//    private MovieReader movieReader = new CsvMovieReader();
//    private MovieReader movieReader = new JaxbMovieReader();

    @Autowired
    public MovieFinder(@Qualifier("csvMovieReader") MovieReader movieReader) {
        this.movieReader = Objects.requireNonNull(movieReader);
    }



    /*
     * 애플리케이션 추가 요구사항:
     *
     * TODO 1. XML 문서로 작성된 영화 메타데이터도 다룰 수 있게 기능을 확장하라
     * TODO 2. 영화 메타데이터 위치를 변경할 수 있도록 하라
     * TODO 3. 영화 메타데이터 읽기 속도를 빠르게 하라
     * TODO 4. 시스템 언어설정에 따라 애플리케이션 메시지가 영어 또는 한글로 출력되게 하라
     */

    /**
     * 저장된 영화 목록에서 감독으로 영화를 검색한다.
     *
     * @param directedBy 감독
     * @return 검색된 영화 목록
     */
    public List<Movie> directedBy(String directedBy) {
        return movieReader.loadMovies().stream()
                .filter(it -> it.getDirector().toLowerCase().contains(directedBy.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * 저장된 영화 목록에서 개봉년도로 영화를 검색한다.
     *
     * @param releasedYearBy
     * @return 검색된 영화 목록
     */
    public List<Movie> releasedYearBy(int releasedYearBy) {
        return movieReader.loadMovies().stream()
                .filter(it -> Objects.equals(it.getReleaseYear(), releasedYearBy))
                .collect(Collectors.toList());
    }


}

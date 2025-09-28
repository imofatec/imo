package com.imo.backend.e2e.factories;

import java.util.ArrayList;
import java.util.List;

import com.imo.backend.e2e.utils.DataFakeFactory;
import com.imo.backend.modules.course.http.dtos.CreateCourseRequest;
import com.imo.backend.modules.lesson.actions.inputs.CreateLessonInput;

public class CourseTestFactory {
 
    public static CreateCourseRequest createValidCourseWithLessons(int index){

        List<CreateLessonInput> lessons = new ArrayList<>();
        for(int i = 0 ; i<=index;i++){
            var lesson = new CreateLessonInput(     
                DataFakeFactory.generateValidTitle(),
                DataFakeFactory.generateValidDescription(),
                DataFakeFactory.generateValidYoutubeLink()
                );
            lessons.add(lesson);
        }

        return new CreateCourseRequest(
            DataFakeFactory.generateValidCourseName(),
             DataFakeFactory.generateValidCourseCategory(),
              DataFakeFactory.generateValidCourseLevel(),
               DataFakeFactory.generateValidCourseDescription(), lessons);
    }    
}

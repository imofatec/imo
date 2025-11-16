import React, { useState } from "react";
import { useRouter } from "expo-router";
import { Text, Pressable, KeyboardAvoidingView, Platform, ScrollView, Alert, View } from "react-native";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useLocalSearchParams } from "expo-router";

import { editCourseSchema } from "../schemas/editCourse";
import { useAllCourses } from "../hooks/useAllCourses";
import { useLessons } from "../hooks/useLessons";

import CourseFormEdit from "../components/editCourse/courseFormEdit";
import LessonListEdit from "../components/editCourse/lessonListEdit";
import NewLessonForm from "../components/editCourse/newLessonForm";
import DeleteCourseButton from "../components/editCourse/deleteCourseButton";

import { editCourseRequest } from "../requests/courses/editCourseRequest";
import { editLessonRequest } from "../requests/courses/editLessonRequest";
import { createNewLessonRequest } from "../requests/courses/createNewLessonRequest";
import { deleteLessonRequest } from "../requests/courses/deleteLessonRequest";
import { deleteCourseRequest } from "../requests/courses/deleteCourseRequest";

export default function EditCourse() {
  const params = useLocalSearchParams();
  const router = useRouter();
  const { courseSlug, courseID } = params;

  const { courses, loading, refetch } = useAllCourses({ nameSlug: courseSlug, size: 1, page: 0 });
  const { lessons, refetch: refetchLesson } = useLessons({ courseNameSlug: courseSlug });

  const [errorMessage, setErrorMessage] = useState(null);
  const [newLessonVisible, setNewLessonVisible] = useState(false);

  const showNewLessonForm = () => setNewLessonVisible(true);
  const hideNewLessonForm = () => setNewLessonVisible(false);

  const { control, handleSubmit, reset } = useForm({
    resolver: zodResolver(editCourseSchema)
  });

  const courseReady = !loading && courses.length > 0;

  async function onEditCourse(data) {
    const response = await editCourseRequest(courseID, data);
    if (!response.success) return setErrorMessage(response.error);
    await refetch();
    setErrorMessage(null);
    reset();
  }

  async function onEditLesson(lessonId, data) {
    const response = await editLessonRequest(lessonId, data);
    if (!response.success) return setErrorMessage(response.error);
    await refetchLesson();
    setErrorMessage(null);
    reset();
  }

  async function onCreateLesson(data) {
    const lessonData = data.lessons[courses[0]?.lessonsCount];
    const submission = {
      title: lessonData.nameLesson,
      description: lessonData.descriptionL,
      youtubeLink: lessonData.link,
    };
    const response = await createNewLessonRequest(courseID, submission);
    if (!response.success) return setErrorMessage(response.error);

    await refetch();
    await refetchLesson();
    setErrorMessage(null);
    hideNewLessonForm();
    reset();
  }

  async function onDeleteLesson(lessonId) {
    const response = await deleteLessonRequest(lessonId);
    if (!response.success) return setErrorMessage(response.error);
    await refetchLesson();
    await refetch();
    setErrorMessage(null);
  }

  async function onDeleteCourse() {
    const response = await deleteCourseRequest(courseID);
    if (response.success) {
      Alert.alert("Sucesso", "O curso foi excluído.");
      router.push("(tabs)/home");
    } else {
      Alert.alert("Erro", response.error || "Não foi possível excluir o curso.");
    }
  }

  return (
    <KeyboardAvoidingView
      className="flex-1 bg-custom-primary"
      behavior={Platform.OS === "android" ? "padding" : "height"}
    >
      <ScrollView
        className="flex-1 px-6 bg-custom-primary"
        keyboardShouldPersistTaps="handled"
        contentContainerStyle={{ justifyContent: "center", flexGrow: 1 }}
      >
        {!courseReady && <Text className="text-white mt-2">Carregando curso...</Text>}

        {courseReady && (
          <>
            <Text className="text-white text-2xl text-center py-4">Editar Curso</Text>

            
            <CourseFormEdit
              control={control}
              courses={courses[0]}
              onEditCourse={handleSubmit(onEditCourse)}
            />

            
            <LessonListEdit
              lessons={lessons}
              control={control}
              onEditLesson={onEditLesson}
              onDeleteLesson={onDeleteLesson}
              handleSubmit={handleSubmit}
            />

            
            {newLessonVisible && (
              <NewLessonForm
                control={control}
                index={courses[0]?.lessonsCount}
                onCancel={hideNewLessonForm}
                onCreate={handleSubmit(onCreateLesson)}
              />
            )}

            {!newLessonVisible && (
              <Pressable
                className="bg-white py-3 rounded-full mb-6"
                onPress={showNewLessonForm}
              >
                <Text className="text-black text-2xl text-center font-bold">Adicionar aula</Text>
              </Pressable>
            )}

            <DeleteCourseButton onDelete={onDeleteCourse} />

            {errorMessage && (
              <Text className="text-red-500 text-center mb-4">{errorMessage}</Text>
            )}
          </>
        )}
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

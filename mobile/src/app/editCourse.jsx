import React, { useState } from "react";
import { useRouter } from "expo-router";
import { Text, Pressable, KeyboardAvoidingView, Platform, View, ScrollView, Alert } from "react-native";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { editCourseSchema } from "../schemas/editCourse";
import { useLocalSearchParams } from "expo-router";
import { useAllCourses } from "../hooks/useAllCourses";
import { useLessons } from "../hooks/useLessons";
import LessonformEdit from "../components/editCourse/lessonformEdit";
import CourseFormEdit from "../components/editCourse/courseFormEdit";
import LessonForm from "../components/createCourse/lessonForm";
import { editCourseRequest } from "../requests/courses/editCourseRequest";
import { editLessonRequest } from "../requests/courses/editLessonRequest";
import { createNewLessonRequest } from "../requests/courses/createNewLessonRequest";
import { deleteLessonRequest } from "../requests/courses/deleteLessonRequest";
import { deleteCourseRequest } from "../requests/courses/deleteCourseRequest";
import { Ionicons } from '@expo/vector-icons';

export default function EditCourse() {
    const params = useLocalSearchParams();
    const router = useRouter();
    const { courseSlug, courseID } = params;
    const { courses, loading, error, refetch } = useAllCourses({ nameSlug: courseSlug, size: 1, page: 0 });
    const { lessons, loading: loadingLesson, error: errorLesson, refetch: refetchLesson } = useLessons({ courseNameSlug: courseSlug });

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
        if (!response.success) {
            setErrorMessage(response.error);
            return;
        }
        await refetch();
        setErrorMessage(null)
        reset();
    }

    async function onEditLesson(lessonId, data) {
        const response = await editLessonRequest(lessonId, data);
        if (!response.success) {
            console.log("error", response.error)
            setErrorMessage(response.error);
            return;
        }
        await refetchLesson();
        setErrorMessage(null)
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

        if (!response.success) {
            setErrorMessage(response.error);
            return;
        }

        await refetch();
        await refetchLesson();
        setErrorMessage(null);
        hideNewLessonForm();
        reset()
    }

    async function onDeleteLesson(lessonId) {
        const response = await deleteLessonRequest(lessonId);
        if (!response.success) {
            setErrorMessage(response.error);
            return;
        }
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

                        <CourseFormEdit control={control} courses={courses[0]} onEditCourse={handleSubmit(onEditCourse)} />

                        <View className="mt-6 mb-2">
                            <Text className="text-white text-2xl text-center font-bold">Editar aulas</Text>
                        </View>

                        {lessons.map((item, index) => (
                            <LessonformEdit
                                key={item.id}
                                control={control}
                                index={index}
                                namePlaceholder={item.title}
                                LinkPlaceholder={item.youtubeLink}
                                descPlaceholder={item.description}
                                onEditLesson={handleSubmit((data) => {
                                    const lessonData = data.lessons[index];
                                    onEditLesson(item.id, lessonData);
                                })}
                                onDeleteLesson={() => {
                                    Alert.alert(
                                        "Excluir aula",
                                        "Tem certeza de que deseja excluir esta aula?\nEssa ação não poderá ser desfeita.",
                                        [
                                            { text: "Cancelar", style: "cancel" },
                                            { text: "Excluir", style: "destructive", onPress: () => onDeleteLesson(item.id) },
                                        ]
                                    );
                                }}
                            />
                        ))}


                        {newLessonVisible && (
                            <>
                                <LessonForm control={control} index={courses[0]?.lessonsCount} />
                                <View className="flex-1 flex-row justify-between mb-6">
                                    <Pressable className="flex-row items-center gap-4"
                                        onPress={hideNewLessonForm}>
                                        <Ionicons name="trash-outline" size={20} color="red" />
                                        <Text className="text-red-500 text-lg">Cancelar</Text>
                                    </Pressable>
                                    <Pressable className="flex-row items-center gap-4"
                                        onPress={handleSubmit((data) => onCreateLesson(data))}>
                                        <Ionicons name="download-outline" size={20} color="green" />
                                        <Text className="text-green-500 text-lg">Criar aula</Text>
                                    </Pressable>
                                </View>
                            </>
                        )}

                        <Pressable className="bg-white py-3 rounded-full mb-6" onPress={showNewLessonForm}>
                            <Text className="text-black text-2xl text-center font-bold">Adicionar aula</Text>
                        </Pressable>

                        <Pressable
                            className="bg-red-600 py-3 rounded-full mb-10"
                            onPress={() => {
                                Alert.alert(
                                    "Excluir curso",
                                    "Tem certeza de que deseja excluir este curso? Essa ação não poderá ser desfeita.",
                                    [
                                        { text: "Cancelar", style: "cancel" },
                                        { text: "Excluir", style: "destructive", onPress: onDeleteCourse },
                                    ]
                                );
                            }}
                        >
                            <Text className="text-white text-2xl text-center font-bold">
                                Excluir curso
                            </Text>
                        </Pressable>


                        {errorMessage && (
                            <Text className="text-red-500 text-center mb-4">{errorMessage}</Text>
                        )}
                    </>
                )}

            </ScrollView>
        </KeyboardAvoidingView>
    );
}

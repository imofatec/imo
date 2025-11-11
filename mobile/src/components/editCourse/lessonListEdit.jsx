import React from "react";
import { View, Text, Alert } from "react-native";
import LessonformEdit from "./lessonformEdit";

export default function LessonListEdit({ lessons, control, onEditLesson, onDeleteLesson,handleSubmit }) {
    return (
        <View className="mt-6">
            <Text className="text-white text-2xl text-center font-bold mb-4">Editar aulas</Text>
            {lessons.map((lesson, index) => (
                <LessonformEdit
                    key={lesson.id}
                    control={control}
                    index={index}
                    namePlaceholder={lesson.title}
                    LinkPlaceholder={lesson.youtubeLink}
                    descPlaceholder={lesson.description}
                    onEditLesson={handleSubmit((data) => {
                        const lessonData = data.lessons[index];
                        onEditLesson(lesson.id, lessonData);
                    })}
                    onDeleteLesson={() =>
                        Alert.alert(
                            "Excluir aula",
                            "Tem certeza de que deseja excluir esta aula?\nEssa ação não poderá ser desfeita.",
                            [
                                { text: "Cancelar", style: "cancel" },
                                { text: "Excluir", style: "destructive", onPress: () => onDeleteLesson(lesson.id) },
                            ]
                        )
                    }
                />
            ))}
        </View>
    );
}

import React from "react";
import LessonForm from "../createCourse/lessonForm";
import { Text, View, Pressable } from "react-native";
import { Ionicons } from '@expo/vector-icons';

export default function LessonFormEdit({control, index,onDeleteLesson,onEditLesson,namePlaceholder,LinkPlaceholder,descPlaceholder}) {
    return (
        <>
            <LessonForm control={control} index={index} namePlaceholder={namePlaceholder} LinkPlaceholder={LinkPlaceholder} descPlaceholder={descPlaceholder}/>
            <View className="flex-1 flex-row justify-between mb-5">
                <Pressable className="flex-row items-center gap-4" onPress={onDeleteLesson}>
                    <Ionicons name="trash-outline" size={20} color="red" />
                    <Text className="text-red-500 text-lg">Excluir aula</Text>
                </Pressable>
                <Pressable className="flex-row items-center gap-4" onPress={onEditLesson}>
                    <Ionicons name="download-outline" size={20} color="green" />
                    <Text className="text-green-500 text-lg">Salvar alterações</Text>
                </Pressable>
            </View>
        </>
    )
}
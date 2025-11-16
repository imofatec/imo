import React from "react";
import { View, Text, Pressable } from "react-native";
import LessonForm from "../../components/createCourse/lessonForm";
import { Ionicons } from '@expo/vector-icons';

export default function NewLessonForm({ control, index, onCancel, onCreate }) {
    return (
        <View>
            <LessonForm control={control} index={index} />
            <View className="flex-row justify-between mb-6">
                <Pressable className="flex-row items-center gap-4" onPress={onCancel}>
                    <Ionicons name="trash-outline" size={20} color="red" />
                    <Text className="text-red-500 text-lg">Cancelar</Text>
                </Pressable>
                <Pressable className="flex-row items-center gap-4" onPress={onCreate}>
                    <Ionicons name="download-outline" size={20} color="green" />
                    <Text className="text-green-500 text-lg">Criar aula</Text>
                </Pressable>
            </View>
        </View>
    );
}

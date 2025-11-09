import React, { useState } from "react";
import { Text, Pressable, KeyboardAvoidingView, Platform, View, ScrollView, Alert } from "react-native";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { createCourseSchema } from "../schemas/createCourse";
import FormInput from "../components/inputs/formInput";
import SelectInput from "../components/inputs/selectInput";
import TextBoxInput from "../components/inputs/textBoxInput";
import LessonForm from "../components/createCourse/lessonForm";

export default function EditCourse() {
    const [errorMessage, setErrorMessage] = useState(null);
    const [lessonsIdx, setLessonsIdx] = useState([0]);

    const { control, handleSubmit, getValues, setValue, reset } = useForm({
        resolver: zodResolver(createCourseSchema),
        defaultValues: {
            nameCourse: "", category: "", level: "", description: "", lessons: [
                { nameLesson: "", link: "", descriptionL: "" },
            ],
        },
    });

    function addLesson() {
        setLessonsIdx((prev) => [...prev, prev.length]);
        const current = getValues("lessons") ?? [];
        setValue("lessons", [...current, { nameLesson: "", link: "", descriptionL: "" }], {
            shouldValidate: false,
        });
    }

    function removeLessonAt(idx) {
        const current = getValues("lessons") ?? [];
        if (current.length <= 1) return;
        const next = [...current];
        next.splice(idx, 1);
        setValue("lessons", next, { shouldValidate: true, shouldDirty: true });
        setLessonsIdx(Array.from({ length: next.length }, (_, i) => i));
    }

    function confirmRemoveLesson(idx) {
        Alert.alert(
            "Remover aula",
            "Tem certeza que deseja remover esta aula?",
            [
                { text: "Cancelar", style: "cancel" },
                { text: "Remover", style: "destructive", onPress: () => removeLessonAt(idx) },
            ]
        );
    }


    function confirmRemoveLastLesson() {
        if (lessonsIdx.length <= 1) return;
        Alert.alert(
            "Remover última aula",
            "Deseja remover a última aula adicionada?",
            [
                { text: "Cancelar", style: "cancel" },
                { text: "Remover", style: "destructive", onPress: () => removeLessonAt(lessonsIdx.length - 1) },
            ]
        );
    }

    async function onSubmit(data) {
        console.log("Curso editado com sucesso:", data);
        Alert.alert("Sucesso", "As alterações foram salvas (simulação).");
        setErrorMessage(null);
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
                <Text className="text-white text-4xl text-center py-4">Editar Curso</Text>

                <FormInput control={control} name="nameCourse" label="Nome do curso" placeholder="Curso de Python" />
                <FormInput control={control} name="category" label="Categoria" placeholder="e.g., design, python, excel" />
                <SelectInput control={control} name="level" label="Nível" />
                <TextBoxInput control={control} name="description" label="Descrição" placeholder="Descrição do curso..." />

                <View className="mt-6 mb-2">
                    <Text className="text-white text-2xl text-center font-bold">Editar aulas</Text>
                </View>

                {lessonsIdx.map((_, index) => (
                    <LessonForm key={index} control={control} index={index} canRemove={lessonsIdx.length > 1} onRemove={() => confirmRemoveLesson(index)} />
                ))}

                <View className="flex-row justify-around mt-4">
                    <Pressable className="border border-white px-6 py-3 rounded-full mb-6" onPress={confirmRemoveLastLesson}>
                        <Text className="text-white text-xl font-bold">Remover aula</Text>
                    </Pressable>

                    <Pressable className="bg-white px-6 py-3 rounded-full mb-6" onPress={addLesson}>
                        <Text className="text-black text-xl font-bold">Adicionar aula</Text>
                    </Pressable>
                </View>

                <Pressable
                    className="bg-white py-3 rounded-full mb-6"
                    onPress={handleSubmit(onSubmit)}
                >
                    <Text className="text-black text-2xl text-center font-bold">Salvar alterações</Text>
                </Pressable>

                {errorMessage && (
                    <Text className="text-red-500 text-center mb-4">{errorMessage}</Text>
                )}
                <Pressable
                    className="bg-red-600 py-3 rounded-full mb-10"
                    onPress={() => {
                        Alert.alert(
                            "Excluir curso",
                            "Tem certeza de que deseja excluir este curso? Essa ação não poderá ser desfeita.",
                            [
                                { text: "Cancelar", style: "cancel" },
                                {
                                    text: "Excluir",
                                    style: "destructive",
                                    onPress: () => {
                                        console.log("Curso excluído");
                                        Alert.alert("Sucesso", "O curso foi excluído.");
                                    }
                                },
                            ]
                        );
                    }}
                >
                    <Text className="text-white text-2xl text-center font-bold">
                        Excluir curso
                    </Text>
                </Pressable>
            </ScrollView>
        </KeyboardAvoidingView>
    );
}

import React from "react";
import { Pressable, Text, Alert } from "react-native";

export default function DeleteCourseButton({ onDelete }) {
    return (
        <Pressable
            className="bg-red-600 py-3 rounded-full mb-10"
            onPress={() =>
                Alert.alert(
                    "Excluir curso",
                    "Tem certeza de que deseja excluir este curso? Essa ação não poderá ser desfeita.",
                    [
                        { text: "Cancelar", style: "cancel" },
                        { text: "Excluir", style: "destructive", onPress: onDelete },
                    ]
                )
            }
        >
            <Text className="text-white text-2xl text-center font-bold">Excluir curso</Text>
        </Pressable>
    );
}

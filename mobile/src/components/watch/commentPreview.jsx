import React from "react";
import { View, Text, Pressable } from "react-native";
import { useUserById } from "../../hooks/useUserById";

export default function CommentsPreview({ latest, count, onPress }) {

  const { user,loading } = useUserById(latest?.userId);

  return (
    <Pressable onPress={onPress} className="mt-3 mx-2 p-3 rounded-2xl bg-white/5 border border-white/10">
      <View className="flex-row items-baseline justify-between">
        <Text className="text-white font-semibold">Comentários</Text>
        <Text className="text-white/70 text-xs">{count} no total</Text>
      </View>

      {latest ? (
        <>
          <Text className="text-white/80 mt-2 number-of-lines-1">
            <Text className="text-white font-semibold">{loading ? "Carregando..." : user?.name}:{" "} </Text>
            {latest.content}
          </Text>
          <Text className="text-white/50 text-custom-header-cyan mt-2">Ver todos</Text>
        </>
      ) : (
        <Text className="text-white/70 mt-2">Ainda não há comentários — toque para abrir.</Text>
      )}
    </Pressable>
  );
}

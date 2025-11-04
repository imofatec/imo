import React from "react";
import { View, Text, Image } from "react-native";
import { useUserById } from "../../hooks/useUserById";

function initials(name = "") {
  const parts = name.trim().split(" ").filter(Boolean);
  return parts.slice(0, 2).map(p => p[0]?.toUpperCase()).join("");
}

export default function CommentItem({ comment }) {
  const { user, loading } = useUserById(comment?.userId);
  return (
    <View className="flex-row gap-3 px-2 py-3">
      {comment.avatarUrl ? (
        <Image source={{ uri: comment.avatarUrl }} className="w-9 h-9 rounded-full" />
      ) : (
        <View className="w-9 h-9 rounded-full bg-white/10 items-center justify-center">
          <Text className="text-white text-xs">{initials(loading ? "Carregando..." : user?.name)}</Text>
        </View>
      )}

      <View className="flex-1">
        <Text className="text-white font-semibold">{loading ? "Carregando..." : user?.name}</Text>
        <Text className="text-white/90 mt-1">{comment.content}</Text>
      </View>
    </View>
  );
}

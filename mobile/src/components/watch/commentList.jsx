import React from "react";
import { View, Text } from "react-native";
import CommentItem from "./commentItem";

export default function CommentsList({ comments }) {
  if (!comments?.length) {
    return (
      <View className="px-2 py-6">
        <Text className="text-white/80">Nenhum comentário ainda - Faça o seu agora!</Text>
      </View>
    );
  }

  return (
    <View style={{ paddingBottom: 24 }}>
      {comments.map((item) => (
        <CommentItem key={item.id} comment={item} />
      ))}
    </View>
  );
}

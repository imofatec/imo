import React from "react";
import { View, Text } from "react-native";
import CommentsList from "./commentList";
import CommentInput from "./commentInput";

export default function CommentsTabContent({
  currentLesson,
  currentComments,
  commentInputY,
  setCommentInputY,
  scrollToComment,
  refetchComments,
}) {
  return (
    <View>
      <Text className="text-white text-xl font-bold mb-3">Comentários</Text>
      <CommentsList comments={currentComments} />

      <View onLayout={(e) => setCommentInputY(e.nativeEvent.layout.y)}>
        <CommentInput
          lessonId={currentLesson?.id}
          parentId={null}
          onSuccess={async () => {
            await refetchComments();
            scrollToComment();
          }}
        />
      </View>
    </View>
  );
}

import { useRef, useEffect } from "react";
import { Animated } from 'react-native';

export function useDrawerAnimation(isOpen) {
    const slideAnim = useRef(new Animated.Value(-300)).current;
    const opacityAnim = useRef(new Animated.Value(0)).current;

    useEffect(() => {
        if (isOpen) {
            Animated.parallel([
                Animated.timing(slideAnim, {
                    toValue: 0,
                    duration: 300,
                    useNativeDriver: true,
                }),
                Animated.timing(opacityAnim, {
                    toValue: 1,
                    duration: 300,
                    useNativeDriver: true,
                }),
            ]).start();
        }
    }, [isOpen]);

    const closeWithAnimation = (onComplete) => {
        Animated.parallel([
            Animated.timing(slideAnim, {
                toValue: -300,
                duration: 300,
                useNativeDriver: true,
            }),
            Animated.timing(opacityAnim, {
                toValue: 0,
                duration: 300,
                useNativeDriver: true,
            }),
        ]).start(() => {
            slideAnim.setValue(-300);
            opacityAnim.setValue(0);
            onComplete();
        });
    };

    return { slideAnim, opacityAnim, closeWithAnimation };
}